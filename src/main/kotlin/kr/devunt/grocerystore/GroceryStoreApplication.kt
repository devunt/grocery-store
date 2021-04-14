package kr.devunt.grocerystore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GroceryStoreApplication

fun main(args: Array<String>) {
    runApplication<GroceryStoreApplication>(*args)
}
