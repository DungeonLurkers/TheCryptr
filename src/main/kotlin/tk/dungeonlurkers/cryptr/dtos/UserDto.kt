package tk.dungeonlurkers.cryptr.dtos

import org.springframework.security.core.GrantedAuthority
import java.util.*

class UserDto (
    val id: UUID,
    val enabled: Boolean,
    val credentialsNonExpired: Boolean,
    val username: String,
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val authorities: MutableCollection<out GrantedAuthority>
) {
    constructor(username: String) :
            this(
                UUID.randomUUID(),
                true,
                true,
                username,
                true,
                true,
                mutableListOf<GrantedAuthority>()
            )
}