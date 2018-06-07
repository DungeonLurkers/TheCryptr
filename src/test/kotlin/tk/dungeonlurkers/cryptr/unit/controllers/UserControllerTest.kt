package tk.dungeonlurkers.cryptr.unit.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import tk.dungeonlurkers.cryptr.controllers.UserController
import tk.dungeonlurkers.cryptr.dtos.UserCreateDto
import tk.dungeonlurkers.cryptr.dtos.UserDto
import tk.dungeonlurkers.cryptr.entities.UserEntity
import tk.dungeonlurkers.cryptr.services.UserEntityDao
import javax.persistence.EntityNotFoundException

@SpringBootTest
class UserControllerTest {

    lateinit var userController: UserController
    lateinit var modelMapper: ModelMapper
    lateinit var userEntityDao: UserEntityDao
    lateinit var mockMvc: MockMvc

    lateinit var userCreateDto: UserCreateDto
    lateinit var userDto: UserDto
    lateinit var userEntity: UserEntity
    lateinit var objectMapper: ObjectMapper

    @Before
    fun setUp() {
        val username = "TestUser1"
        val password = "TestPass"
        val email = "test@sup.pl"

        userCreateDto = UserCreateDto(password, username, email)
        userDto = UserDto(username, email)
        userEntity = UserEntity(username, password, email)
        objectMapper = ObjectMapper()

        modelMapper = mock(ModelMapper::class.java)
        userEntityDao = mock(UserEntityDao::class.java)

        userController = UserController(modelMapper, userEntityDao)

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build()


        `when`(modelMapper.map(userDto, UserEntity::class.java)).thenReturn(userEntity)
        `when`(modelMapper.map(userEntity, UserDto::class.java)).thenReturn(userDto)
    }

    @Test
    fun addUserSuccess() {
        val jsonContent = objectMapper.writeValueAsString(userCreateDto)

        `when`(modelMapper.map(any(UserCreateDto::class.java), eq(UserEntity::class.java)))
            .thenReturn(userEntity)
        doNothing().`when`(userEntityDao).saveOrUpdate(userEntity)

        mockMvc
            .perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isCreated)

        verify(userEntityDao, times(1)).saveOrUpdate(userEntity)
    }

    @Test
    fun addUserNoUsernameError() {
        val userNoUsername = UserCreateDto(userCreateDto.password, "", userCreateDto.email)
        val jsonContent = objectMapper.writeValueAsString(userNoUsername)

        mockMvc
            .perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().string(containsString("NO_USERNAME")))
    }

    @Test
    fun addUserNoEmailError() {
        val userNoUsername = UserCreateDto(userCreateDto.password, userCreateDto.username, "")
        val jsonContent = objectMapper.writeValueAsString(userNoUsername)

        mockMvc
            .perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().string(containsString("NO_EMAIL")))
    }

    @Test
    fun addUserNoPasswordError() {
        val userNoUsername = UserCreateDto("", userCreateDto.username, userCreateDto.email)
        val jsonContent = objectMapper.writeValueAsString(userNoUsername)

        mockMvc
            .perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().string(containsString("NO_PASSWORD")))
    }

    @Test
    fun getUserByIdFound() {
        `when`(userEntityDao.findById(userEntity.id)).thenReturn(userEntity)

        mockMvc
            .perform(get("/users/id/${userEntity.id}")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().string(containsString(userEntity.username)))
            .andExpect(content().string(containsString(userEntity.email)))

    }

    @Test
    fun getUserByUsernameFound() {
        `when`(userEntityDao.findByUsername(userEntity.username)).thenReturn(userEntity)

        mockMvc
            .perform(get("/users/${userEntity.username}")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().string(containsString(userEntity.username)))
            .andExpect(content().string(containsString(userEntity.email)))
    }

    @Test
    fun getUserByIdNotFound() {
        `when`(userEntityDao.findById(userEntity.id)).thenReturn(null)

        mockMvc
            .perform(get("/users/${userEntity.username}")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)

    }

    @Test
    fun getUserByUsernameNotFound() {
        `when`(userEntityDao.findByUsername(userEntity.username)).thenReturn(null)

        mockMvc
            .perform(get("/users/id/${userEntity.id}")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }

    @Test
    fun deleteUserSuccess() {
        doNothing().`when`(userEntityDao).delete(userEntity.id)

        mockMvc
            .perform(delete("/users/${userEntity.id}")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun deleteUserNotFound() {
        `when`(userEntityDao.delete(userEntity.id)).thenThrow(EntityNotFoundException("Entity not found!"))

        mockMvc
            .perform(delete("/users/${userEntity.id}")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }
}