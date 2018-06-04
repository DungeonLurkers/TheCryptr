package tk.dungeonlurkers.cryptr.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tk.dungeonlurkers.cryptr.entities.MessageEntity
import tk.dungeonlurkers.cryptr.repos.MessageEntityRepository
import tk.dungeonlurkers.cryptr.services.MessageEntityDao
import java.util.*

@Service
class MessageEntityServiceImpl(
    @Autowired
    private val messageEntityRepository: MessageEntityRepository
) : MessageEntityDao {

    override fun getAll(): List<MessageEntity> = messageEntityRepository.findAll()

    override fun saveOrUpdate(messageEntity: MessageEntity) {
        messageEntityRepository.save(messageEntity)
    }

    override fun findById(id: UUID): MessageEntity? {
        return messageEntityRepository.findById(id).get()
    }

    override fun delete(id: UUID) {
        messageEntityRepository.deleteById(id)
    }
}