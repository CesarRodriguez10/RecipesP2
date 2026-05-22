package com.example.recipesp2.ui.theme.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesp2.data.model.RecipeStats
import com.example.recipesp2.data.repository.RecipeRepository
import com.example.recipesp2.domain.usecase.GetStatsUseCase
import kotlinx.coroutines.launch

class StatsViewModel : ViewModel() {

    private val repository = RecipeRepository()
    private val getStatsUseCase = GetStatsUseCase(repository)

    private val _stats = MutableLiveData<RecipeStats>()
    val stats: LiveData<RecipeStats> = _stats

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loadStats(recipeId: Int) {
        viewModelScope.launch {
            _loading.value = true
            getStatsUseCase(recipeId).fold(
                onSuccess = { _stats.value = it },
                onFailure = { _error.value = it.message ?: "Error al cargar estadísticas" }
            )
            _loading.value = false
        }
    }
}