package kr.devunt.grocerystore.controller

import kr.devunt.grocerystore.controller.dto.CategoryDto
import kr.devunt.grocerystore.controller.dto.ItemDto
import kr.devunt.grocerystore.controller.dto.KeyDto
import kr.devunt.grocerystore.db.model.Category
import kr.devunt.grocerystore.db.repository.ItemRepository
import kr.devunt.grocerystore.service.CategoryService
import kr.devunt.grocerystore.service.JwtService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GroceryStoreController(
    private val jwtService: JwtService,
    private val categoryService: CategoryService,
    private val itemRepository: ItemRepository,
) {
    @GetMapping("/token")
    fun token(@RequestHeader(HttpHeaders.HOST) host: String): Any {
        val category = categoryService.get(host)
        val token = jwtService.issue(category.slug)
        return when (category.tokenIssueType) {
            Category.TokenIssueType.HEADER ->
                ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, ResponseCookie.from("Authorization", token).build().toString())
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(null)

            Category.TokenIssueType.BODY ->
                KeyDto(accessToken = token)
        }
    }

    @GetMapping("/categories")
    fun categories(): List<CategoryDto> = categoryService.all().map { CategoryDto(name = it.name, slug = it.slug) }

    @GetMapping("/product", "/item")
    fun item(
        @RequestHeader("host") host: String,
        @RequestParam name: String?,
    ): Any {
        val category = categoryService.get(host)
        return if (name == null) {
            val items = itemRepository.findByCategory(category)
            items.map { it.name }
        } else {
            val item = itemRepository.findByNameAndCategory(name, category)
            ItemDto(name = item.name, price = item.price)
        }
    }
}
