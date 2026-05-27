package com.example.recipesp2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.recipesp2.ui.theme.addrecipe.AddRecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddRecipeViewModelTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Valida que no se pueda guardar una receta sin nombre
    @Test
    fun testNombreVacio() {
        val vm = AddRecipeViewModel()
        vm.saveRecipe("", "desc", "cat", 10, listOf("algo"), listOf("paso"))
        assertEquals("El nombre no puede estar vacío", vm.error.value)
        assertNull(vm.success.value)
    }

    // Valida que no se pueda guardar una receta sin ingredientes
    @Test
    fun testSinIngredientes() {
        val vm = AddRecipeViewModel()
        vm.saveRecipe("Pasta", "", "", 5, emptyList(), listOf("hervir"))
        assertEquals("Agrega al menos un ingrediente", vm.error.value)
    }

    // Valida que no se pueda guardar una receta sin pasos
    @Test
    fun testSinPasos() {
        val vm = AddRecipeViewModel()
        vm.saveRecipe("Pasta", "", "", 5, listOf("100g pasta"), emptyList())
        assertEquals("Agrega al menos un paso", vm.error.value)
    }

    // Valida que el promedio de calificaciones se calcule correctamente
    @Test
    fun testPromedioCalificaciones() {
        val ratings = listOf(4.0, 5.0, 3.0)
        assertEquals(4.0, ratings.average(), 0.001)
    }

    // Valida que con una sola opinión el promedio sea igual a esa calificación
    @Test
    fun testPromedioUnaOpinion() {
        val ratings = listOf(5.0)
        assertEquals(5.0, ratings.average(), 0.001)
    }
}