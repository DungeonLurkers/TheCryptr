package tk.dungeonlurkers.cryptr.repos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tk.dungeonlurkers.cryptr.entities.MessageEntity
import java.util.*

@Repository
interface MessageEntityRepository : JpaRepository<MessageEntity, UUID>