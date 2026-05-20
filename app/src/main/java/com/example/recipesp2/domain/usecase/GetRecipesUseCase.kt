package com.example.recipesp2.domain.usecase

import com.example.recipesp2.data.model.Recipe
import com.example.recipesp2.data.repository.RecipeRepository

// Caso de uso: obtener lista de recetas
class GetRecipesUseCase(private val repository: RecipeRepository) {
    suspend operator fun invoke(): Result<List<Recipe>> = repository.getRecipes()
}