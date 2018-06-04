package tk.dungeonlurkers.cryptr.unit.services

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import tk.dungeonlurkers.cryptr.entities.MessageEntity
import tk.dungeonlurkers.cryptr.repos.MessageEntityRepository
import tk.dungeonlurkers.cryptr.services.MessageEntityDao
import tk.dungeonlurkers.cryptr.services.impl.MessageEntityServiceImpl
import java.time.Instant
import java.util.*

@SpringBootTest
class MessageDaoTest {
    lateinit var messageEntityService: MessageEntityDao
    lateinit var messageEntityRepository: MessageEntityRepository
    lateinit var messageEntity: MessageEntity
    lateinit var messageEntityNoId: MessageEntity

    @Before
    fun setUp() {
        messageEntityRepository = mock(MessageEntityRepository::class.java)
        messageEntityService = MessageEntityServiceImpl(messageEntityRepository)

        val date = Date.from(Instant.now())
        val messageBody = "TestMessageLel"
        val messageId = UUID.randomUUID()

        messageEntity = MessageEntity(messageId, messageBody, date)
        messageEntityNoId = MessageEntity(UUID.randomUUID(), messageBody, date)
    }

    @Test
    fun saveOrUpdateTest() {
        `when`(messageEntityRepository.save(messageEntityNoId)).thenReturn(messageEntity)

        messageEntityService.saveOrUpdate(messageEntityNoId)

        verify(messageEntityRepository, times(1)).save(messageEntityNoId)
    }

    @Test
    fun findByIdTest() {
        `when`(messageEntityRepository.findById(messageEntity.id)).thenReturn(Optional.of(messageEntity))

        val message = messageEntityService.findById(messageEntity.id)

        assert(message!! == messageEntity)
        verify(messageEntityRepository, times(1)).findById(messageEntity.id)
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