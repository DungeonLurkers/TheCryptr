package tk.dungeonlurkers.cryptr.services

import tk.dungeonlurkers.cryptr.entities.UserAuthorityEntity
import java.util.*


interface UserAuthorityEntityDao {
    fun saveOrUpdate(authorityEntity: UserAuthorityEntity)
    fun findById(id: UUID): UserAuthorityEntity?
    fun delete(id: UUID)
    fun getAll(): List<UserAuthorityEntity>
}