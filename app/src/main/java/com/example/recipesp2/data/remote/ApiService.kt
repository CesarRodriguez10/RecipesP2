package com.example.recipesp2.data.remote

import com.example.recipesp2.data.model.Opinion
import com.example.recipesp2.data.model.Recipe
import com.example.recipesp2.data.model.RecipeStats
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("recipes")
    suspend fun getRecipes(): Response<List<Recipe>>

    @GET("recipes/{id}")
    suspend fun getRecipeById(@Path("id") id: Int): Response<Recipe>

    @POST("recipes")
    suspend fun createRecipe(@Body recipe: Recipe): Response<Map<String, Any>>

    @GET("recipes/{id}/opinions")
    suspend fun getOpinions(@Path("id") id: Int): Response<List<Opinion>>

    @POST("recipes/{id}/opinions")
    suspend fun addOpinion(
        @Path("id") id: Int,
        @Body opinion: Opinion
    ): Response<Map<String, Any>>

    @GET("recipes/{id}/stats")
    suspend fun getStats(@Path("id") id: Int): Response<RecipeStats>
}