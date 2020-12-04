package com.ensicaen.springbootlibrary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootLibraryApplication

fun main(args: Array<String>) {
    runApplication<SpringBootLibraryApplication>(*args)
}