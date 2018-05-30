package tk.dungeonlurkers.cryptr.unit.services

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import tk.dungeonlurkers.cryptr.services.MessageEntityRepository
import tk.dungeonlurkers.cryptr.services.MessageEntityService
import tk.dungeonlurkers.cryptr.services.MessageEntityServiceImpl

@SpringBootTest
class MessageServiceTest {
    lateinit var messageEntityService: MessageEntityService
    lateinit var messageEntityRepository: MessageEntityRepository


    @Before
    fun setUp() {
        messageEntityRepository = mock(MessageEntityRepository::class.java)
        messageEntityService = MessageEntityServiceImpl(messageEntityRepository)
    }

    @Test
    fun saveOrUpdateTest() {

    }

    @Test
    fun findByIdTest() {

    }

    @Test
    fun getAllTest() {

    }

    @Test
    fun deleteTest() {

    }
}