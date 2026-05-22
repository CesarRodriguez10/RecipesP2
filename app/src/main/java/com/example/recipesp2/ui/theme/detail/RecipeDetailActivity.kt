package com.example.recipesp2.ui.theme.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.recipesp2.databinding.ActivityRecipeDetailBinding
import com.example.recipesp2.ui.theme.opinion.AddOpinionActivity
import com.example.recipesp2.ui.theme.stats.StatsActivity

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    private val viewModel: RecipeDetailViewModel by viewModels()
    private var recipeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId == -1) { finish(); return }

        observeViewModel()
        viewModel.loadRecipe(recipeId)

        binding.btnAddOpinion.setOnClickListener {
            val intent = Intent(this, AddOpinionActivity::class.java)
            intent.putExtra("RECIPE_ID", recipeId)
            startActivity(intent)
        }

        binding.btnStats.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            intent.putExtra("RECIPE_ID", recipeId)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        // Recarga para reflejar nuevas opiniones
        viewModel.loadRecipe(recipeId)
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.recipe.observe(this) { recipe ->
            binding.tvName.text = recipe.name
            binding.tvCategory.text = recipe.category ?: "Sin categoría"
            binding.tvDescription.text = recipe.description ?: "Sin descripción"
            binding.tvPrepTime.text = "⏱ ${recipe.prep_time ?: "?"} minutos"
            binding.tvRating.text = "⭐ %.1f (%d opiniones)".format(
                recipe.avg_rating, recipe.opinion_count
            )
            binding.tvTimesPrepared.text = "Preparada ${recipe.times_prepared} veces"

            // Ingredientes
            binding.tvIngredients.text = if (recipe.ingredients.isEmpty())
                "Sin ingredientes"
            else recipe.ingredients.joinToString("\n") { "• $it" }

            // Pasos
            binding.tvSteps.text = if (recipe.steps.isEmpty())
                "Sin pasos"
            else recipe.steps.mapIndexed { i, step -> "${i + 1}. $step" }.joinToString("\n")

            if (!recipe.image_url.isNullOrBlank()) {
                Glide.with(this).load(recipe.image_url).into(binding.ivRecipe)
            }
        }

        viewModel.opinions.observe(this) { opinions ->
            if (opinions.isEmpty()) {
                binding.tvOpinions.text = "Aún no hay opiniones"
            } else {
                binding.tvOpinions.text = opinions.joinToString("\n\n") { op ->
                    "⭐ ${op.rating} — ${op.comment ?: "Sin comentario"} (${op.created_at?.take(10) ?: ""})"
                }
            }
        }

        viewModel.error.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}