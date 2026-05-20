package com.example.recipesp2.data.model

data class RecipeStats(
    val recipe_name: String,
    val times_prepared: Int,
    val avg_rating: Double,
    val avg_portions: Double,
    val total_opinions: Int,
    val history: List<Opinion>
)