package kr.devunt.grocerystore.db.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Category(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(nullable = false, unique = true)
    val slug: String,
)
