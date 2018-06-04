package tk.dungeonlurkers.cryptr.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tk.dungeonlurkers.cryptr.entities.UserAuthorityEntity
import tk.dungeonlurkers.cryptr.repos.UserAuthorityEntityRepository
import tk.dungeonlurkers.cryptr.services.UserAuthorityEntityDao
import java.util.*

@Service
class UserAuthorityEntityDaoImpl(
    @Autowired val userAuthorityEntityRepository: UserAuthorityEntityRepository
) : UserAuthorityEntityDao {
    override fun saveOrUpdate(authorityEntity: UserAuthorityEntity) {
        userAuthorityEntityRepository.save(authorityEntity)
    }

    override fun findById(id: UUID): UserAuthorityEntity? {
        return userAuthorityEntityRepository.findById(id).get()
    }

    override fun delete(id: UUID) {
        userAuthorityEntityRepository.deleteById(id)
    }

    override fun getAll(): List<UserAuthorityEntity> {
        return userAuthorityEntityRepository.findAll()
    }
}