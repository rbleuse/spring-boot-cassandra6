package com.github.rbleuse.springbootcassandra6

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.cassandra.CassandraContainer
import org.testcontainers.utility.DockerImageName
import org.testcontainers.utility.MountableFile
import java.time.Duration

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	fun cassandraContainer(): CassandraContainer {
		return CassandraContainer(
			DockerImageName
				.parse("rbleuse/apache-cassandra:6.0-alpha1")
				.asCompatibleSubstituteFor("cassandra")
		)
		.withEnv("CASSANDRA_ENDPOINT_SNITCH", "GossipingPropertyFileSnitch")
		.withCopyFileToContainer(
			MountableFile.forHostPath("dev-tools/cassandra.yaml"),
			"/etc/cassandra/cassandra.yaml"
		).withInitScript("init.cql")
		.withStartupTimeout(Duration.ofMinutes(2))
	}
}
