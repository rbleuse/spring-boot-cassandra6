package com.github.rbleuse.springbootcassandra6

import org.flywaydb.core.Flyway
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn

@Configuration
@EnableConfigurationProperties(FlywayConfiguration.FlywayProperties::class)
class FlywayConfiguration {
    @ConfigurationProperties("spring.flyway")
    data class FlywayProperties(
        val url: String,
        val user: String? = null,
        val password: String? = null,
        val locations: List<String> = listOf("classpath:db/migration"),
        val sqlMigrationSuffixes: List<String> = listOf(".cql"),
        val defaultSchema: String? = null,
    )

    @Bean(initMethod = "migrate")
    @DependsOn("cqlSession")
    fun flyway(props: FlywayProperties): Flyway {
        return Flyway.configure()
            .dataSource(props.url, props.user, props.password)
            .locations(*props.locations.toTypedArray())
            .sqlMigrationSuffixes(*props.sqlMigrationSuffixes.toTypedArray())
            .apply { props.defaultSchema?.let { defaultSchema(it) } }
            .load()
    }
}
