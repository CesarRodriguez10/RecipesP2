package com.example.recipesp2.ui.theme.stats

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recipesp2.databinding.ActivityStatsBinding

class StatsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatsBinding
    private val viewModel: StatsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId == -1) { finish(); return }

        binding.btnBack.setOnClickListener { finish() }

        observeViewModel()
        viewModel.loadStats(recipeId)
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.stats.observe(this) { stats ->
            binding.tvRecipeName.text = stats.recipe_name
            binding.tvAvgRating.text = "%.1f".format(stats.avg_rating)
            binding.tvTimesPrepared.text = "${stats.times_prepared}"
            binding.tvTotalOpinions.text = "${stats.total_opinions}"
            binding.tvAvgPortions.text = "%.0f porciones".format(stats.avg_portions)

            // Historial de preparaciones
            if (stats.history.isEmpty()) {
                binding.tvHistory.text = "Sin historial aún"
            } else {
                binding.tvHistory.text = stats.history.joinToString("\n") { op ->
                    "${op.created_at?.take(10) ?: "?"} — ⭐ ${op.rating} — ${op.portions ?: "?"} porciones"
                }
            }
        }

        viewModel.error.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}