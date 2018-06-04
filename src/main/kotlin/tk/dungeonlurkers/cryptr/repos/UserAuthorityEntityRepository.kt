package tk.dungeonlurkers.cryptr.repos

import org.springframework.data.jpa.repository.JpaRepository
import tk.dungeonlurkers.cryptr.entities.UserAuthorityEntity
import java.util.*

interface UserAuthorityEntityRepository : JpaRepository<UserAuthorityEntity, UUID>