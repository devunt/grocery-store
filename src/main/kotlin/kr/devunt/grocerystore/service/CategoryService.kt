package kr.devunt.grocerystore.service

import kr.devunt.grocerystore.db.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
) {
    fun get(host: String) = categoryRepository.findBySlug(host.substringBefore("."))
}
