package com.example.recipesp2.ui.theme.addrecipe

import androidx.lifecycle.*
import com.example.recipesp2.data.model.Recipe
import com.example.recipesp2.data.repository.RecipeRepository
import kotlinx.coroutines.launch

class AddRecipeViewModel : ViewModel() {

    private val repository = RecipeRepository()

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun saveRecipe(name: String, description: String, category: String,
                   prepTime: Int, ingredients: List<String>, steps: List<String>) {
        if (name.isBlank()) {
            _error.value = "El nombre no puede estar vacío"
            return
        }
        val recipe = Recipe(
            name = name, description = description, category = category,
            prep_time = prepTime, ingredients = ingredients, steps = steps
        )
        viewModelScope.launch {
            repository.createRecipe(recipe).fold(
                onSuccess = { _success.value = true },
                onFailure = { _error.value = it.message }
            )
            _loading.value = false
        }
    }
}