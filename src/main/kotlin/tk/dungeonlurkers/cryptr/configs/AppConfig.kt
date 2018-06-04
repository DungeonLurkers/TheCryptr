package tk.dungeonlurkers.cryptr.configs

import org.modelmapper.ModelMapper
import org.modelmapper.spring.SpringIntegration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AppConfig {

    @Bean
    fun getLogger(injectionPoint: InjectionPoint): Logger {
        return LoggerFactory.getLogger(injectionPoint.methodParameter?.containingClass)
    }

    @Bean
    fun getModelMapper(beanFactory: BeanFactory): ModelMapper {
        val mapper = ModelMapper()
        val springProvider = SpringIntegration.fromSpring(beanFactory)
        mapper.configuration.provider = springProvider
        return mapper
    }

    @Bean
    fun getPasswordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}