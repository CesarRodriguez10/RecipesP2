package com.example.recipesp2.ui.theme.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesp2.data.model.Recipe
import com.example.recipesp2.data.repository.RecipeRepository
import com.example.recipesp2.domain.usecase.GetRecipesUseCase
import kotlinx.coroutines.launch

class RecipeListViewModel : ViewModel() {

    private val repository = RecipeRepository()
    private val getRecipesUseCase = GetRecipesUseCase(repository)

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loadRecipes() {
        viewModelScope.launch {
            _loading.value = true
            getRecipesUseCase().fold(
                onSuccess = { _recipes.value = it },
                onFailure = { _error.value = it.message }
            )
            _loading.value = false
        }
    }
}