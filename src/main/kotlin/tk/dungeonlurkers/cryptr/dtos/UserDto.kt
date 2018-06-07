package tk.dungeonlurkers.cryptr.dtos

import org.springframework.security.core.GrantedAuthority
import java.util.*

class UserDto (
    val id: UUID = UUID(0 ,0),
    val enabled: Boolean = true,
    val credentialsNonExpired: Boolean = true,
    val username: String = "",
    val accountNonExpired: Boolean = true,
    val accountNonLocked: Boolean = true,
    val authorities: MutableCollection<out GrantedAuthority> = mutableListOf<GrantedAuthority>(),
    val email: String = ""
) {
    constructor(username: String, email: String) :
            this(
                UUID.randomUUID(),
                true,
                true,
                username,
                true,
                true,
                mutableListOf<GrantedAuthority>(),
                email
            )
}