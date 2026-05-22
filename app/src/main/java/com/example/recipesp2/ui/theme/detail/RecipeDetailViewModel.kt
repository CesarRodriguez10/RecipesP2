package com.example.recipesp2.ui.theme.detail

import androidx.lifecycle.*
import com.example.recipesp2.data.model.*
import com.example.recipesp2.data.repository.RecipeRepository
import com.example.recipesp2.domain.usecase.*
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RecipeDetailViewModel : ViewModel() {

    private val repository = RecipeRepository()
    private val getDetailUseCase = GetRecipeDetailUseCase(repository)

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> = _recipe

    private val _opinions = MutableLiveData<List<Opinion>>()
    val opinions: LiveData<List<Opinion>> = _opinions

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loadRecipe(id: Int) {
        viewModelScope.launch {
            getDetailUseCase(id).fold(
                onSuccess = { _recipe.value = it },
                onFailure = { _error.value = it.message }
            )
            repository.getOpinions(id).fold(
                onSuccess = { _opinions.value = it },
                onFailure = {  }
            )
        }
    }
}