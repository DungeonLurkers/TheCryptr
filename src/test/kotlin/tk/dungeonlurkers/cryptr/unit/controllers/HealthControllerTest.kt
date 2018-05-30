package tk.dungeonlurkers.cryptr.unit.controllers

import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import tk.dungeonlurkers.cryptr.controllers.HealthController

@SpringBootTest
class HealthControllerTest {
    lateinit var healthController: HealthController
    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        healthController = HealthController()
        mockMvc = MockMvcBuilders.standaloneSetup(healthController).build()
    }

    @Test
    fun okSuccessTest() {
        mockMvc
                .perform(get("/health"))
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("OK")))
    }
}