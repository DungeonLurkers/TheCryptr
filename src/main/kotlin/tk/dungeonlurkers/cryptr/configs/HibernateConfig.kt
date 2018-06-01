package tk.dungeonlurkers.cryptr.configs

import org.hibernate.SessionFactory
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ["tk.dungeonlurkers.cryptr.repos"], entityManagerFactoryRef = "sessionFactory")
class HibernateConfig {

    @Autowired
    @Bean(name = ["sessionFactory"])
    fun getSessionFactory(@Qualifier("dataSource") dataSource: DataSource): SessionFactory {
        val sessionBuilder = LocalSessionFactoryBuilder(dataSource)
        sessionBuilder.scanPackages("tk.avabin.cryptr.entities")
        sessionBuilder.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect")
        sessionBuilder.setProperty(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, "false")
        sessionBuilder.setProperty(AvailableSettings.HBM2DDL_AUTO, "update")
        sessionBuilder.setProperty(AvailableSettings.DEFAULT_SCHEMA, "public")
        // sessionBuilder.setProperty(AvailableSettings.SHOW_SQL, "true")
        // sessionBuilder.setProperty(AvailableSettings.FORMAT_SQL, "true")
        return sessionBuilder.buildSessionFactory()
    }

    @Bean(name = ["dataSource"])
    fun getDataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.postgresql.Driver")
        dataSource.url = System.getenv("POSTGRES_URL")
        dataSource.schema = System.getenv("POSTGRES_DB")
        dataSource.username = System.getenv("POSTGRES_USER")
        dataSource.password = System.getenv("POSTGRES_PASSWORD")
        return dataSource
    }
}