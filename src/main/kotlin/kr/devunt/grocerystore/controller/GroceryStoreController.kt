package kr.devunt.grocerystore.controller

import kr.devunt.grocerystore.controller.dto.ItemDto
import kr.devunt.grocerystore.db.repository.CategoryRepository
import kr.devunt.grocerystore.db.repository.ItemRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GroceryStoreController(
    private val categoryRepository: CategoryRepository,
    private val itemRepository: ItemRepository,
) {
    @GetMapping("/product", "/item")
    fun get(
        @RequestHeader("host") host: String,
        @RequestParam name: String?,
    ): Any {
        val category = categoryRepository.findBySlug(host.substringBefore('.'))
        return if (name == null) {
            val items = itemRepository.findByCategory(category)
            items.map { it.name }
        } else {
            val item = itemRepository.findByNameAndCategory(name, category)
            ItemDto(name = item.name, price = item.price)
        }
    }
}
