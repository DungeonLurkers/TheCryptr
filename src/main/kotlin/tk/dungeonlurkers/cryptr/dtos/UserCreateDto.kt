package tk.dungeonlurkers.cryptr.dtos

class UserCreateDto(
    var password: String = "",
    val username: String = "",
    val email: String = ""
)