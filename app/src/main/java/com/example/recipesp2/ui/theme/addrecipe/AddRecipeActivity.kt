package com.example.recipesp2.ui.theme.addrecipe

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recipesp2.databinding.ActivityAddRecipeBinding

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding
    private val viewModel: AddRecipeViewModel by viewModels()

    // Listas dinámicas de ingredientes y pasos
    private val ingredientsList = mutableListOf<String>()
    private val stepsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.btnIncrement.setOnClickListener {
            val current = binding.etPortions.text.toString().toIntOrNull() ?: 1
            binding.etPortions.setText((current + 1).toString())
        }

        binding.btnDecrement.setOnClickListener {
            val current = binding.etPortions.text.toString().toIntOrNull() ?: 1
            if (current > 1) binding.etPortions.setText((current - 1).toString())
        }

        // Agregar ingrediente a la lista
        binding.btnAddIngredient.setOnClickListener {
            val ingredient = binding.etIngredient.text.toString().trim()
            if (ingredient.isNotBlank()) {
                ingredientsList.add(ingredient)
                binding.etIngredient.text?.clear()
                refreshIngredients()
            }
        }

        // Agregar paso a la lista
        binding.btnAddStep.setOnClickListener {
            val step = binding.etStep.text.toString().trim()
            if (step.isNotBlank()) {
                stepsList.add(step)
                binding.etStep.text?.clear()
                refreshSteps()
            }
        }

        binding.btnSave.setOnClickListener {
            viewModel.saveRecipe(
                name = binding.etName.text.toString(),
                description = binding.etDescription.text.toString(),
                category = binding.etCategory.text.toString(),
                prepTime = binding.etPrepTime.text.toString().toIntOrNull() ?: 0,
                ingredients = ingredientsList.toList(),
                steps = stepsList.toList()
            )
        }

        observeViewModel()
    }

    private fun refreshIngredients() {
        binding.tvIngredients.text = ingredientsList
            .joinToString("\n") { "• $it" }
    }

    private fun refreshSteps() {
        binding.tvSteps.text = stepsList
            .mapIndexed { i, s -> "${i + 1}. $s" }
            .joinToString("\n")
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnSave.isEnabled = !isLoading
        }
        viewModel.success.observe(this) {
            Toast.makeText(this, "✅ Receta guardada", Toast.LENGTH_SHORT).show()
            finish()
        }
        viewModel.error.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}