package tk.dungeonlurkers.cryptr.services

import tk.dungeonlurkers.cryptr.entities.MessageEntity
import java.util.*


interface MessageEntityDao {
    fun saveOrUpdate(messageEntity: MessageEntity)
    fun findById(id: UUID): MessageEntity?
    fun delete(id: UUID)
    fun getAll(): List<MessageEntity>
}
