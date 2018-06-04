package tk.dungeonlurkers.cryptr.unit.services

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import tk.dungeonlurkers.cryptr.entities.UserAuthorityEntity
import tk.dungeonlurkers.cryptr.repos.UserAuthorityEntityRepository
import tk.dungeonlurkers.cryptr.services.impl.UserAuthorityEntityDaoImpl
import java.util.*

@SpringBootTest
class UserAuthorityEntityDaoTest {
    lateinit var userAuthorityEntityDao: UserAuthorityEntityDaoImpl
    lateinit var userAuthorityEntityRepository: UserAuthorityEntityRepository
    lateinit var userAuthorityEntity: UserAuthorityEntity

    @Before
    fun setUp() {
        val id = UUID.randomUUID()
        val authority = "USER"
        userAuthorityEntityRepository = mock(UserAuthorityEntityRepository::class.java)
        userAuthorityEntity = UserAuthorityEntity(id, authority)
        userAuthorityEntityDao = UserAuthorityEntityDaoImpl(userAuthorityEntityRepository)
    }

    @Test
    fun saveOrUpdate() {
        `when`(userAuthorityEntityRepository.save(userAuthorityEntity)).thenReturn(userAuthorityEntity)

        userAuthorityEntityDao.saveOrUpdate(userAuthorityEntity)

        verify(userAuthorityEntityRepository, times(1)).save(userAuthorityEntity)
    }

    @Test
    fun findById() {
        `when`(userAuthorityEntityRepository.findById(userAuthorityEntity.id)).thenReturn(Optional.of(userAuthorityEntity))

        val userAuthority = userAuthorityEntityDao.findById(userAuthorityEntity.id)

        assert(userAuthority == userAuthorityEntity)
        verify(userAuthorityEntityRepository, times(1)).findById(userAuthorityEntity.id)
    }

    @Test
    fun delete() {
        doNothing().`when`(userAuthorityEntityRepository).deleteById(userAuthorityEntity.id)

        userAuthorityEntityDao.delete(userAuthorityEntity.id)

        verify(userAuthorityEntityRepository, times(1)).deleteById(userAuthorityEntity.id)
    }

    @Test
    fun getAll() {
        val list = listOf(userAuthorityEntity)
        `when`(userAuthorityEntityRepository.findAll()).thenReturn(list)

        val all = userAuthorityEntityDao.getAll()

        assert(list == all)
        verify(userAuthorityEntityRepository, times(1)).findAll()
    }
}
