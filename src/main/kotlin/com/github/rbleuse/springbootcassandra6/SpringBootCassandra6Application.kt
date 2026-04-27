package com.github.rbleuse.springbootcassandra6

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootCassandra6Application

fun main(args: Array<String>) {
    runApplication<SpringBootCassandra6Application>(*args)
}
