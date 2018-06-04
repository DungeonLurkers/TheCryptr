package tk.dungeonlurkers.cryptr.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import tk.dungeonlurkers.cryptr.services.UserEntityDao

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    @Autowired val userEntityDao: UserEntityDao,
    @Autowired val passwordEncoder: PasswordEncoder
) : WebSecurityConfigurerAdapter(true) {

    override fun configure(auth: AuthenticationManagerBuilder) {
        super.configure(auth)
        auth.userDetailsService(userEntityDao).passwordEncoder(passwordEncoder)
    }
}