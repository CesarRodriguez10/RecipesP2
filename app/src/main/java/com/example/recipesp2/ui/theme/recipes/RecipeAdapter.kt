package com.example.recipesp2.ui.theme.recipes


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipesp2.data.model.Recipe
import com.example.recipesp2.databinding.ItemRecipeBinding

class RecipeAdapter(
    private var recipes: List<Recipe>,
    private val onClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.tvName.text = recipe.name
            binding.tvRating.text = "⭐ %.1f (%d)".format(recipe.avg_rating, recipe.opinion_count)
            binding.tvCategory.text = recipe.category ?: "Sin categoría"
            binding.tvTimesPrepared.text = "Preparada ${recipe.times_prepared} veces"

            if (!recipe.image_url.isNullOrBlank()) {
                Glide.with(binding.root.context)
                    .load(recipe.image_url)
                    .into(binding.ivRecipe)
            }

            binding.root.setOnClickListener { onClick(recipe) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(recipes[position])

    override fun getItemCount() = recipes.size

    // Actualiza la lista (usado también para filtrar búsqueda)
    fun updateList(newList: List<Recipe>) {
        recipes = newList
        notifyDataSetChanged()
    }
}