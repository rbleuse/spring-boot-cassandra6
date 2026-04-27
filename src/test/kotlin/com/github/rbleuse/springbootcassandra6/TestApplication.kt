package com.github.rbleuse.springbootcassandra6

import org.springframework.boot.fromApplication
import org.springframework.boot.with

fun main(args: Array<String>) {
    fromApplication<SpringBootCassandra6Application>().with(TestcontainersConfiguration::class).run(*args)
}
