package tk.dungeonlurkers.cryptr.controllers

import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tk.dungeonlurkers.cryptr.dtos.MessageDto
import tk.dungeonlurkers.cryptr.entities.MessageEntity
import tk.dungeonlurkers.cryptr.services.MessageEntityService

@RestController
@RequestMapping("message")
class MessageController(
        @Autowired private val messageEntityService: MessageEntityService,
        @Autowired private val logger: Logger,
        @Autowired private val modelMapper: ModelMapper
) {

    @PostMapping
    fun createMessage(@RequestBody messageDto: MessageDto): ResponseEntity<MessageEntity> {
        try {
            messageEntityService.saveOrUpdate(modelMapper.map(messageDto, MessageEntity::class.java))
            logger.debug("Message saved in db")
        } catch (e: Exception) {
            logger.error(e.message)
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("all")
    fun getAllMessages(): ResponseEntity<List<MessageDto>> {
        val all = messageEntityService.getAll()
        if (all.isEmpty()) return ResponseEntity(HttpStatus.NOT_FOUND)
        val mappedAll = all.mapTo(ArrayList<MessageDto>(), { messageEntity ->
            modelMapper.map(messageEntity, MessageDto::class.java)
        })
        return ResponseEntity(mappedAll, HttpStatus.OK)
    }

    @GetMapping("{id}")
    fun getMessage(@PathVariable id: Int): ResponseEntity<MessageEntity> {
        val message = messageEntityService.findById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(message, HttpStatus.OK)
    }

}