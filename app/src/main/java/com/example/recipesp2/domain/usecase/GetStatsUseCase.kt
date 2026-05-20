package com.example.recipesp2.domain.usecase

import com.example.recipesp2.data.model.RecipeStats
import com.example.recipesp2.data.repository.RecipeRepository

class GetStatsUseCase(private val repository: RecipeRepository) {
    suspend operator fun invoke(recipeId: Int): Result<RecipeStats> =
        repository.getStats(recipeId)
}