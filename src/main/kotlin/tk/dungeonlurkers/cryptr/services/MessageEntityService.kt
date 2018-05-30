package tk.dungeonlurkers.cryptr.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import tk.dungeonlurkers.cryptr.entities.MessageEntity

@Repository
interface MessageEntityRepository : JpaRepository<MessageEntity, Int> {
    fun findOneById(id: Int): MessageEntity?
}

interface MessageEntityService {
    fun saveOrUpdate(messageEntity: MessageEntity)
    fun findById(id: Int): MessageEntity?
    fun delete(id: Int)
    fun getAll(): List<MessageEntity>
}

@Service
class MessageEntityServiceImpl(
        @Autowired
        private val messageEntityRepository: MessageEntityRepository
) : MessageEntityService {

    override fun getAll(): List<MessageEntity> = messageEntityRepository.findAll()

    override fun saveOrUpdate(messageEntity: MessageEntity) {
        messageEntityRepository.save(messageEntity)
    }

    override fun findById(id: Int): MessageEntity? {
        return messageEntityRepository.findOneById(id)
    }

    override fun delete(id: Int) {
        messageEntityRepository.deleteById(id)
    }

}