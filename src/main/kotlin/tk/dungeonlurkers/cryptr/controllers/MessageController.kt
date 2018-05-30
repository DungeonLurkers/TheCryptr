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
        if (messageDto.id == 0) return ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY)
        val mapped = modelMapper.map(messageDto, MessageEntity::class.java)
        messageEntityService.saveOrUpdate(mapped)
        logger.debug("Message saved in db")
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