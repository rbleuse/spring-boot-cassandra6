package com.github.rbleuse.springbootcassandra6

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.cassandra.CassandraContainer
import org.testcontainers.utility.DockerImageName
import org.testcontainers.utility.MountableFile
import java.time.Duration

object CassandraContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val cassandraContainer = CassandraContainer(
            DockerImageName
                .parse("rbleuse/apache-cassandra:6.0-alpha1")
                .asCompatibleSubstituteFor("cassandra")
        )
            .withEnv("CASSANDRA_ENDPOINT_SNITCH", "GossipingPropertyFileSnitch")
            .withCopyFileToContainer(
                MountableFile.forHostPath("dev-tools/cassandra.yaml"),
                "/etc/cassandra/cassandra.yaml"
            ).withStartupTimeout(Duration.ofMinutes(2))

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        cassandraContainer.start()

        val contactPoint = "${cassandraContainer.contactPoint.hostName}:${cassandraContainer.contactPoint.port}"
        val keyspace = applicationContext.environment.getRequiredProperty("spring.cassandra.keyspace-name")
        val localDc = cassandraContainer.localDatacenter
        val flywayUrl = "jdbc:cassandra://$contactPoint/$keyspace?localdatacenter=$localDc&requesttimeout=5000"

        TestPropertyValues.of(
            mapOf(
                "spring.cassandra.contact-points" to contactPoint,
                "spring.cassandra.local-datacenter" to localDc,
                "spring.flyway.url" to flywayUrl,
            )
        ).applyTo(applicationContext.environment)
    }
}
