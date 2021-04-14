package kr.devunt.grocerystore.db.repository

import kr.devunt.grocerystore.db.model.Category
import org.springframework.data.repository.CrudRepository

interface CategoryRepository : CrudRepository<Category, Long> {
    fun findBySlug(slug: String): Category
}
