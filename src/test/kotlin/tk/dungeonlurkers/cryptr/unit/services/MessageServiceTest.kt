package tk.dungeonlurkers.cryptr.unit.services

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import tk.dungeonlurkers.cryptr.entities.MessageEntity
import tk.dungeonlurkers.cryptr.services.MessageEntityRepository
import tk.dungeonlurkers.cryptr.services.MessageEntityService
import tk.dungeonlurkers.cryptr.services.MessageEntityServiceImpl
import java.time.Instant
import java.util.*

@SpringBootTest
class MessageServiceTest {
    lateinit var messageEntityService: MessageEntityService
    lateinit var messageEntityRepository: MessageEntityRepository
    lateinit var messageEntity: MessageEntity
    lateinit var messageEntityNoId: MessageEntity


    @Before
    fun setUp() {
        messageEntityRepository = mock(MessageEntityRepository::class.java)
        messageEntityService = MessageEntityServiceImpl(messageEntityRepository)

        val date = Date.from(Instant.now())
        val messageBody = "TestMessageLel"
        val messageId = 1

        messageEntity = MessageEntity(messageId, messageBody, date)
        messageEntityNoId = MessageEntity(0, messageBody, date)
    }

    @Test
    fun saveOrUpdateTest() {
        `when`(messageEntityRepository.save(messageEntityNoId)).thenReturn(messageEntity)

        messageEntityService.saveOrUpdate(messageEntityNoId)

        verify(messageEntityRepository, times(1)).save(messageEntityNoId)
    }

    @Test
    fun findByIdTest() {
        `when`(messageEntityRepository.findOneById(1)).thenReturn(messageEntity)

        val message = messageEntityService.findById(1)

        assert(message!! == messageEntity)
    }

    @Test
    fun getAllTest() {
        val list = List(1, { _ -> messageEntity })
        `when`(messageEntityRepository.findAll()).thenReturn(list)

        val retval = messageEntityService.getAll()

        assert(list == retval)
    }

    @Test
    fun deleteTest() {
        doNothing().`when`(messageEntityRepository).deleteById(messageEntity.id)

        messageEntityService.delete(messageEntity.id)

        verify(messageEntityRepository, times(1)).deleteById(messageEntity.id)
    }
}