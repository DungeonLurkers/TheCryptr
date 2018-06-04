package tk.dungeonlurkers.cryptr.services

import org.springframework.security.core.userdetails.UserDetailsService
import tk.dungeonlurkers.cryptr.entities.UserEntity
import java.util.*


interface UserEntityDao : UserDetailsService {
    fun saveOrUpdate(userEntity: UserEntity)
    fun findById(id: UUID): UserEntity?
    fun findByUsername(username: String): UserEntity?
    fun delete(id: UUID)
    fun getAll(): List<UserEntity>
}