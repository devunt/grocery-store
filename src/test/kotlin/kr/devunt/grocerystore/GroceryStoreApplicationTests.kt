package kr.devunt.grocerystore

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isNotNull
import strikt.spring.statusCodeIs

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GroceryStoreApplicationTests(
    @Autowired private val restTemplate: TestRestTemplate,
) {
    @Test
    fun `Index page should contain app entry`() {
        val response = restTemplate.exchange<String>("/", HttpMethod.GET)
        expectThat(response).statusCodeIs(HttpStatus.OK)
        expectThat(response.body).isNotNull().contains("app.js")
    }
}
