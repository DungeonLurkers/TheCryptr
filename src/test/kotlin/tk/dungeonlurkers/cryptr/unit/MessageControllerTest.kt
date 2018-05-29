package tk.dungeonlurkers.cryptr.unit

import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.SessionFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tk.dungeonlurkers.cryptr.controllers.MessageController
import tk.dungeonlurkers.cryptr.dtos.MessageDto
import tk.dungeonlurkers.cryptr.entities.MessageEntity
import tk.dungeonlurkers.cryptr.services.MessageEntityService
import java.time.Instant
import java.util.*

@Profile("test")
@RunWith(SpringJUnit4ClassRunner::class)
@AutoConfigureMockMvc
@SpringBootTest
class MessageControllerTest {
    @Autowired
    lateinit var messageController: MessageController
    @Autowired
    lateinit var messageEntityService: MessageEntityService
    @Autowired
    lateinit var modelMapper: ModelMapper
    @InjectMocks
    lateinit var hibernateSessionFactory: SessionFactory

    @LocalServerPort
    var serverPort: Int = 0

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun createMessageSuccessfulTest() {
        // Arrange
        val date = Date.from(Instant.now())
        val messageBody = "TestMessageLel"
        val messageId = 1
        val testMessageDto = MessageDto(messageId, messageBody, date)
        val objectMapper = ObjectMapper()
        val mapped = modelMapper.map(testMessageDto, MessageEntity::class.java)


        `when`(messageEntityService.saveOrUpdate(mapped)).thenReturn(Unit)
        `when`(modelMapper.map(testMessageDto, MessageEntity::class.java)).thenReturn(mapped)

        // Act
        mockMvc.perform(post("/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testMessageDto)))
                .andExpect(status().isCreated)

        // Assert
        verify(messageEntityService, times(1)).saveOrUpdate(mapped)
        verify(messageController, times(1)).createMessage(testMessageDto)
    }
}