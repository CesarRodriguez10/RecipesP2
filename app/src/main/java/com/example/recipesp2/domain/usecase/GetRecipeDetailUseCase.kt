package com.example.recipesp2.domain.usecase

import com.example.recipesp2.data.model.Recipe
import com.example.recipesp2.data.repository.RecipeRepository

class GetRecipeDetailUseCase(private val repository: RecipeRepository) {
    suspend operator fun invoke(id: Int): Result<Recipe> = repository.getRecipeById(id)
}