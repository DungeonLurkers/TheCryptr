package tk.dungeonlurkers.cryptr.unit.services

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.springframework.security.core.userdetails.UserDetails
import tk.dungeonlurkers.cryptr.dtos.UserDto
import tk.dungeonlurkers.cryptr.entities.UserEntity
import tk.dungeonlurkers.cryptr.repos.UserEntityRepository
import tk.dungeonlurkers.cryptr.services.UserEntityDao
import tk.dungeonlurkers.cryptr.services.impl.UserEntityDaoImpl
import java.util.*

class UserEntityDaoTest {
    lateinit var userEntity: UserEntity
    lateinit var userDto: UserDto
    lateinit var userEntityDao: UserEntityDao
    lateinit var userEntityRepository: UserEntityRepository

    @Before
    fun setUp() {
        val username = "testUserName"
        val password = "testPass"
        val email = "testMail@mail.pl"

        userEntity = UserEntity(username, password, email)
        userDto = UserDto(
            userEntity.id,
            userEntity.isEnabled,
            userEntity.isCredentialsNonExpired,
            userEntity.username,
            userEntity.isAccountNonExpired,
            userEntity.isAccountNonLocked,
            userEntity.authorities,
            userEntity.email
        )

        userEntityRepository = mock(UserEntityRepository::class.java)
        userEntityDao = UserEntityDaoImpl(userEntityRepository)
    }

    @Test
    fun saveOrUpdateTest() {
        `when`(userEntityRepository.save(userEntity)).thenReturn(userEntity)

        userEntityDao.saveOrUpdate(userEntity)

        verify(userEntityRepository, times(1)).save(userEntity)
    }

    @Test
    fun findByIdTest() {
        `when`(userEntityRepository.findById(userEntity.id)).thenReturn(Optional.of(userEntity))

        val user = userEntityDao.findById(userEntity.id)

        assert(user!! == userEntity)
        verify(userEntityRepository, times(1)).findById(userEntity.id)
    }

    @Test
    fun findByUsernameTest() {
        `when`(userEntityRepository.findOneByUsername(userEntity.username)).thenReturn(userEntity)

        val user = userEntityDao.findByUsername(userEntity.username)

        assert(user!! == userEntity)
        verify(userEntityRepository, times(1)).findOneByUsername(userEntity.username)
    }

    @Test
    fun getAllTest() {
        val list = listOf(userEntity)
        `when`(userEntityRepository.findAll()).thenReturn(list)

        val users = userEntityDao.getAll()

        assert(list == users)
        verify(userEntityRepository, times(1)).findAll()
    }

    @Test
    fun deleteTest() {
        doNothing().`when`(userEntityRepository).deleteById(userEntity.id)

        userEntityDao.delete(userEntity.id)

        verify(userEntityRepository, times(1)).deleteById(userEntity.id)
    }

    @Test
    fun loadUserDetailsByUsernameTest() {
        `when`(userEntityRepository.findOneByUsername(userEntity.username)).thenReturn(userEntity)

        val userDetails = userEntityDao.loadUserByUsername(userEntity.username)

        verify(userEntityRepository, times(1)).findOneByUsername(userEntity.username)
        assert(userDetails == userEntity as UserDetails)

    }

}