package kr.devunt.grocerystore.db.repository

import kr.devunt.grocerystore.db.model.Category
import kr.devunt.grocerystore.db.model.Item
import org.springframework.data.repository.CrudRepository

interface ItemRepository : CrudRepository<Item, Long> {
    fun findByCategory(category: Category): List<Item>
    fun findByNameAndCategory(name: String, category: Category): Item
}
