package com.example.recipesp2.data.model

data class Opinion(
    val id: Int = 0,
    val recipe_id: Int,
    val comment: String? = null,
    val rating: Double,
    val portions: Int? = null,
    val created_at: String? = null
)