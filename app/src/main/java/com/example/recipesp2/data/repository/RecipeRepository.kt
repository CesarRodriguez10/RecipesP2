package com.example.recipesp2.data.repository


import com.example.recipesp2.data.model.Opinion
import com.example.recipesp2.data.model.Recipe
import com.example.recipesp2.data.model.RecipeStats
import com.example.recipesp2.data.remote.RetrofitClient

class RecipeRepository {

    private val api = RetrofitClient.api

    suspend fun getRecipes(): Result<List<Recipe>> = runCatching {
        val response = api.getRecipes()
        if (response.isSuccessful) response.body()!!
        else error("Error ${response.code()}: ${response.message()}")
    }

    suspend fun getRecipeById(id: Int): Result<Recipe> = runCatching {
        val response = api.getRecipeById(id)
        if (response.isSuccessful) response.body()!!
        else error("Error ${response.code()}: ${response.message()}")
    }

    suspend fun createRecipe(recipe: Recipe): Result<Unit> = runCatching {
        val response = api.createRecipe(recipe)
        if (!response.isSuccessful) error("Error ${response.code()}: ${response.message()}")
    }

    suspend fun getOpinions(recipeId: Int): Result<List<Opinion>> = runCatching {
        val response = api.getOpinions(recipeId)
        if (response.isSuccessful) response.body()!!
        else error("Error ${response.code()}: ${response.message()}")
    }

    suspend fun addOpinion(recipeId: Int, opinion: Opinion): Result<Unit> = runCatching {
        val response = api.addOpinion(recipeId, opinion)
        if (!response.isSuccessful) error("Error ${response.code()}: ${response.message()}")
    }

    suspend fun getStats(recipeId: Int): Result<RecipeStats> = runCatching {
        val response = api.getStats(recipeId)
        if (response.isSuccessful) response.body()!!
        else error("Error ${response.code()}: ${response.message()}")
    }
}