package kr.devunt.grocerystore.db.model

import javax.persistence.*

@Entity
class Item(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @ManyToOne(optional = false)
    val category: Category,

    @Column(nullable = false)
    val price: Int,
)
