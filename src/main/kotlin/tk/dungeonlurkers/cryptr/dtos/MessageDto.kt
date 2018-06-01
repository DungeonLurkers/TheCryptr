package tk.dungeonlurkers.cryptr.dtos

import java.time.Instant
import java.util.*

class MessageDto(
    var id: Int = 0,
    var body: String = "",
    var createTime: Date = Date.from(Instant.EPOCH)
)