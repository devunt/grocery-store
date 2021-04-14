package kr.devunt.grocerystore.db

import kr.devunt.grocerystore.db.model.Category
import kr.devunt.grocerystore.db.model.Item
import kr.devunt.grocerystore.db.repository.CategoryRepository
import kr.devunt.grocerystore.db.repository.ItemRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class FixtureLoader(
    private val categoryRepository: CategoryRepository,
    private val itemRepository: ItemRepository,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        val fruits = Category(name = "과일", slug = "fruit", tokenIssueType = Category.TokenIssueType.BODY)
        val vegetables = Category(name = "채소", slug = "vegetable", tokenIssueType = Category.TokenIssueType.HEADER)

        categoryRepository.saveAll(listOf(fruits, vegetables))
        itemRepository.saveAll(
            listOf(
                Item(name = "배", category = fruits, price = 11),
                Item(name = "토마토", category = fruits, price = 22),
                Item(name = "사과", category = fruits, price = 33),
                Item(name = "바나나", category = fruits, price = 44),

                Item(name = "치커리", category = vegetables, price = 55),
                Item(name = "토마토", category = vegetables, price = 66),
                Item(name = "깻잎", category = vegetables, price = 77),
                Item(name = "상추", category = vegetables, price = 88),
            )
        )
    }
}
