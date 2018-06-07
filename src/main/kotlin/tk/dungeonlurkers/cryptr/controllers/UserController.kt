package tk.dungeonlurkers.cryptr.controllers

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tk.dungeonlurkers.cryptr.dtos.UserCreateDto
import tk.dungeonlurkers.cryptr.dtos.UserDto
import tk.dungeonlurkers.cryptr.entities.UserEntity
import tk.dungeonlurkers.cryptr.services.UserEntityDao
import java.util.*
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("users")
class UserController(
    @Autowired val modelMapper: ModelMapper,
    @Autowired val userEntityDao: UserEntityDao
) {

    @GetMapping("/id/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<UserDto> {
        val userOptional = userEntityDao.findById(id)
        return constructResponse(userOptional)
    }

    @GetMapping("{username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<UserDto> {
        val userOptional = userEntityDao.findByUsername(username)
        return constructResponse(userOptional)
    }

    @PostMapping
    fun createUser(@RequestBody userCreateDto: UserCreateDto): ResponseEntity<Any> {
        var errorMessage = ""
        if (userCreateDto.username == "") errorMessage = "{NO_USERNAME}"
        if (userCreateDto.password == "") errorMessage = "#$errorMessage{NO_PASSWORD}"
        if (userCreateDto.email == "") errorMessage = "$errorMessage{NO_EMAIL}"
        if (errorMessage != "") return ResponseEntity(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY)
        val user = modelMapper.map(userCreateDto, UserEntity::class.java)
        userEntityDao.saveOrUpdate(user)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping("{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Unit> {
        return try {
            userEntityDao.delete(id)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    private fun constructResponse(userEntity: UserEntity?): ResponseEntity<UserDto> {
        return if (userEntity != null)
            ResponseEntity(
                modelMapper.map(userEntity, UserDto::class.java),
                HttpStatus.OK
            )
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }
}