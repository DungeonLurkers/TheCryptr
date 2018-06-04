package tk.dungeonlurkers.cryptr.repos

import org.springframework.data.jpa.repository.JpaRepository
import tk.dungeonlurkers.cryptr.entities.UserEntity
import java.util.*

interface UserEntityRepository : JpaRepository<UserEntity, UUID> {
    fun findOneByUsername(username: String): UserEntity?
}