package me.ethereum.fruit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FruitApplication

fun main(args: Array<String>) {
    runApplication<FruitApplication>(*args)
}
