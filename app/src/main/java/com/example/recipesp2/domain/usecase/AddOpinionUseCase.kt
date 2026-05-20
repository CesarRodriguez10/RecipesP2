package com.example.recipesp2.domain.usecase

import com.example.recipesp2.data.model.Opinion
import com.example.recipesp2.data.repository.RecipeRepository

class AddOpinionUseCase(private val repository: RecipeRepository) {
    suspend operator fun invoke(recipeId: Int, opinion: Opinion): Result<Unit> =
        repository.addOpinion(recipeId, opinion)
}