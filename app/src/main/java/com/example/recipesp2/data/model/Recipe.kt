package com.example.recipesp2.data.model

data class Recipe(
    val id: Int = 0,
    val name: String,
    val description: String? = null,
    val category: String? = null,
    val prep_time: Int? = null,
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList(),
    val image_url: String? = null,
    val times_prepared: Int = 0,
    val avg_rating: Double = 0.0,
    val opinion_count: Int = 0
)