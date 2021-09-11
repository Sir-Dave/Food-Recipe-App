package com.sirdave.foodrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sirdave.foodrecipeapp.util.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel.recipes.value
                for (recipe in recipes){
                    Log.d(TAG, "${recipe.title}")
                }

                Column(modifier = Modifier.padding(16.dp)){
                    Text(text = "RecipeListFragment" )
                }
                /**LazyColumn{
                    itemsIndexed(items = allRecipes){
                        _, recipe ->
                        RecipeCard(recipe = recipe, onClick = {})
                    }
                }*/
            }
        }
    }
}