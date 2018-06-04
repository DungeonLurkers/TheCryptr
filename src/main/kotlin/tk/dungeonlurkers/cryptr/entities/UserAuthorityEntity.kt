package tk.dungeonlurkers.cryptr.entities

import org.hibernate.annotations.GenericGenerator
import org.springframework.security.core.GrantedAuthority
import java.util.*
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id

data class UserAuthorityEntity(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID,
    private val authority: String
) : GrantedAuthority {

    override fun getAuthority() = authority
}