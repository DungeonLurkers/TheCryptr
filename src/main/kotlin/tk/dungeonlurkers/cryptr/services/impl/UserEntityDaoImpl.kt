package tk.dungeonlurkers.cryptr.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import tk.dungeonlurkers.cryptr.entities.UserEntity
import tk.dungeonlurkers.cryptr.repos.UserEntityRepository
import tk.dungeonlurkers.cryptr.services.UserEntityDao
import java.util.*

@Service
class UserEntityDaoImpl(
    @Autowired val userEntityRepository: UserEntityRepository
) : UserEntityDao {
    override fun loadUserByUsername(username: String?): UserDetails {
        return findByUsername(username!!)!!
    }

    override fun saveOrUpdate(userEntity: UserEntity) {
        userEntityRepository.save(userEntity)
    }

    override fun findById(id: UUID): UserEntity? {
        return userEntityRepository.findById(id).get()
    }

    override fun findByUsername(username: String): UserEntity? {
        return userEntityRepository.findOneByUsername(username)
    }

    override fun delete(id: UUID) {
        userEntityRepository.deleteById(id)
    }

    override fun getAll(): List<UserEntity> {
        return userEntityRepository.findAll()
    }
}