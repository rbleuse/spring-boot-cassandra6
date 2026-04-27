package com.github.rbleuse.springbootcassandra6

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.CqlSessionBuilder
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.core.cql.generator.CreateKeyspaceCqlGenerator
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification
import java.time.Duration

@Configuration
class CassandraConfiguration {

    @Bean
    fun cqlSession(
        builder: CqlSessionBuilder,
        @Value("\${spring.cassandra.keyspace-name}") keyspaceName: String,
    ): CqlSession {
        builder.withKeyspace("system").build().use { session ->
            val cql = CreateKeyspaceCqlGenerator
                .toCql(CreateKeyspaceSpecification.createKeyspace(keyspaceName).ifNotExists())
            session.execute(
                SimpleStatement.newInstance(cql).setTimeout(Duration.ofSeconds(30))
            )
        }

        return builder.withKeyspace(keyspaceName).build()
    }
}
