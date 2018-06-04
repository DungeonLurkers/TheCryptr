package tk.dungeonlurkers.cryptr.unit.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.Mockito.any
import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import tk.dungeonlurkers.cryptr.controllers.MessageController
import tk.dungeonlurkers.cryptr.dtos.MessageDto
import tk.dungeonlurkers.cryptr.entities.MessageEntity
import tk.dungeonlurkers.cryptr.services.MessageEntityDao
import java.time.Instant
import java.util.*

@SpringBootTest
class MessageControllerTest {
    lateinit var messageController: MessageController
    lateinit var messageEntityService: MessageEntityDao
    lateinit var modelMapper: ModelMapper
    lateinit var logger: Logger
    lateinit var mockMvc: MockMvc
    lateinit var messageEntity: MessageEntity
    lateinit var messageDto: MessageDto

    @Before
    fun setUp() {
        modelMapper = mock(ModelMapper::class.java)
        messageEntityService = mock(MessageEntityDao::class.java)
        logger = LoggerFactory.getLogger(MessageController::class.java)
        messageController = MessageController(messageEntityService, logger, modelMapper)
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build()

        val date = Date.from(Instant.now())
        val messageBody = "TestMessageLel"
        val messageId = UUID.randomUUID()

        messageDto = MessageDto(messageId, messageBody, date)
        messageEntity = MessageEntity(messageId, messageBody, date)
    }

    @Test
    fun createMessageSuccessTest() {
        // Arrange
        val objectMapper = ObjectMapper()
        val jsonContent = objectMapper.writeValueAsString(messageDto)

        `when`(modelMapper.map(any(MessageDto::class.java), eq(MessageEntity::class.java)))
                .thenReturn(messageEntity)
        doNothing().`when`(messageEntityService).saveOrUpdate(messageEntity)

        // Act
        mockMvc
                .perform(post("/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated)

        // Assert
        verify(messageEntityService, times(1)).saveOrUpdate(messageEntity)
    }

    @Test
    fun createMessageErrorTest() {
        // Arrange
        val jsonContent = "{}"

        // Act
        mockMvc
                .perform(post("/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                // Assert
                .andExpect(status().isUnprocessableEntity)
    }

    @Test
    fun getAllMessageSuccessTest() {
        // Arrange
        val messages = List(1, init = { _ -> messageEntity })

        `when`(messageEntityService.getAll()).thenReturn(messages)
        `when`(modelMapper.map(any(MessageEntity::class.java), eq(MessageDto::class.java)))
                .thenReturn(messageDto)
        // Act
        mockMvc
                .perform(get("/message/all"))
                // Assert
                .andExpect(status().isOk)
                .andExpect(content().string(containsString(messageDto.body)))
    }

    @Test
    fun getMessageByIdSuccessTest() {
        // Arrange
        val id = messageEntity.id
        `when`(messageEntityService.findById(id)).thenReturn(messageEntity)
        `when`(modelMapper.map(any(MessageEntity::class.java), eq(MessageDto::class.java)))
                .thenReturn(messageDto)

        // Act
        mockMvc
                .perform(get("/message/$id"))
                // Assert
                .andExpect(status().isOk)
                .andExpect(content().string(containsString(messageDto.body)))
    }
}