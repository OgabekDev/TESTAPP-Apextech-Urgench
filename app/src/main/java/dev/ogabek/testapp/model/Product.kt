package dev.ogabek.testapp.model

import java.io.Serializable

data class Product(
    val errorMessage: String? = null,
    val id: Long? = null,
    val name: String? = null,
    val photoUrl: String? = null,
    val description: String? = null,
    val price: Double? = null
): Serializable