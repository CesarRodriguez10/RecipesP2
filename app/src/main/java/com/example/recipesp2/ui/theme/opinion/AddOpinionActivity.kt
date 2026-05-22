package com.example.recipesp2.ui.theme.opinion

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.example.recipesp2.databinding.ActivityAddOpinionBinding

class AddOpinionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddOpinionBinding
    private val viewModel: AddOpinionViewModel by viewModels()
    private var recipeId: Int = -1
    private var selectedRating: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOpinionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId == -1) { finish(); return }

        binding.btnBack.setOnClickListener { finish() }

        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            selectedRating = rating.toDouble()
            binding.tvRatingValue.text = "%.1f".format(selectedRating)
        }

        binding.btnSubmit.setOnClickListener {
            val portions = binding.etPortions.text.toString().toIntOrNull() ?: 1
            viewModel.submitOpinion(
                recipeId = recipeId,
                comment = binding.etComment.text.toString(),
                rating = selectedRating,
                portions = portions
            )
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnSubmit.isEnabled = !isLoading
        }
        viewModel.success.observe(this) {
            Toast.makeText(this, "Opinión registrada", Toast.LENGTH_SHORT).show()
            finish()
        }
        viewModel.error.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}