package tk.dungeonlurkers.cryptr.entities

import org.hibernate.annotations.GenericGenerator
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class UserEntity(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID,
    private val enabled: Boolean,
    private val credentialsNonExpired: Boolean,
    private val password: String,
    private val username: String,
    private val accountNonExpired: Boolean,
    private val accountNonLocked: Boolean,
    private val authorities: MutableCollection<out GrantedAuthority>
) : UserDetails {
    constructor(username: String, password: String):
            this(
                UUID.randomUUID(),
                true,
                true,
                password,
                username,
                true,
                true,
                mutableListOf<GrantedAuthority>()
            )
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities
    override fun isEnabled(): Boolean = enabled
    override fun getUsername(): String = username
    override fun isCredentialsNonExpired(): Boolean = credentialsNonExpired
    override fun getPassword(): String = password
    override fun isAccountNonExpired(): Boolean = accountNonExpired
    override fun isAccountNonLocked(): Boolean = accountNonLocked
}