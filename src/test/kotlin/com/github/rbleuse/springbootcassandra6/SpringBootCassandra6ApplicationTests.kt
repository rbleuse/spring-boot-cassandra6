package com.github.rbleuse.springbootcassandra6

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestcontainersConfiguration::class)
class SpringBootCassandra6ApplicationTests {

    @Test
    fun contextLoads() {
    }
}
