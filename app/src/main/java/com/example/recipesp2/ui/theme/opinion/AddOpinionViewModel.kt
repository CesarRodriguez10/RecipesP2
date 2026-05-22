package com.example.recipesp2.ui.theme.opinion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesp2.data.model.Opinion
import com.example.recipesp2.data.repository.RecipeRepository
import com.example.recipesp2.domain.usecase.AddOpinionUseCase
import kotlinx.coroutines.launch

class AddOpinionViewModel : ViewModel() {

    private val repository = RecipeRepository()
    private val addOpinionUseCase = AddOpinionUseCase(repository)

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun submitOpinion(recipeId: Int, comment: String, rating: Double, portions: Int) {
        val opinion = Opinion(
            recipe_id = recipeId,
            comment = comment.trim().ifBlank { null },
            rating = rating,
            portions = portions
        )
        viewModelScope.launch {
            _loading.value = true
            addOpinionUseCase(recipeId, opinion).fold(
                onSuccess = { _success.value = true },
                onFailure = { _error.value = it.message ?: "Error al enviar opinión, reintente" }
            )
            _loading.value = false

        }
    }
}