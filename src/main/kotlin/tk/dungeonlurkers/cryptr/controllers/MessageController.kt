package tk.dungeonlurkers.cryptr.controllers

import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tk.dungeonlurkers.cryptr.dtos.MessageDto
import tk.dungeonlurkers.cryptr.entities.MessageEntity
import tk.dungeonlurkers.cryptr.services.MessageEntityDao
import java.util.UUID
import kotlin.collections.ArrayList

@RestController
@RequestMapping("message")
class MessageController(
    @Autowired private val messageEntityService: MessageEntityDao,
    @Autowired private val logger: Logger,
    @Autowired private val modelMapper: ModelMapper
) {

    @PostMapping
    fun createMessage(@RequestBody messageDto: MessageDto): ResponseEntity<MessageEntity> {
        if (messageDto.id == UUID(0, 0)) return ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY)
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
    fun getMessage(@PathVariable id: UUID): ResponseEntity<MessageEntity> {
        val message = messageEntityService.findById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(message, HttpStatus.OK)
    }
}