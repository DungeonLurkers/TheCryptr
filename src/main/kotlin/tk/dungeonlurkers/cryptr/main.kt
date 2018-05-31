package tk.dungeonlurkers.cryptr

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["tk.dungeonlurkers.cryptr.services"], entityManagerFactoryRef = "sessionFactory")
@ComponentScan(basePackages = ["tk.dungeonlurkers.cryptr"])
class ServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(ServerApplication::class.java, *args)
}
