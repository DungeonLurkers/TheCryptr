package tk.dungeonlurkers.cryptr.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("health")
@RestController
class HealthController {

    @GetMapping
    fun health(): ResponseEntity<String> {
        return ResponseEntity("OK", HttpStatus.OK)

    }
}