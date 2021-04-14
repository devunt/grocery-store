package kr.devunt.grocerystore.db.repository

import kr.devunt.grocerystore.db.model.Item
import org.springframework.data.repository.CrudRepository

interface ItemRepository : CrudRepository<Item, Long>
