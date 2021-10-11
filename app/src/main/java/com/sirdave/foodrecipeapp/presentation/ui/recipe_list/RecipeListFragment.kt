package com.sirdave.foodrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sirdave.foodrecipeapp.presentation.BaseApplication
import com.sirdave.foodrecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.sirdave.foodrecipeapp.presentation.components.LoadingRecipeListShimmer
import com.sirdave.foodrecipeapp.presentation.components.SearchAppBar
import com.sirdave.foodrecipeapp.ui.FoodRecipeAppTheme
import com.sirdave.foodrecipeapp.presentation.components.RecipeCard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                FoodRecipeAppTheme(darkTheme = application.isDark.value) {

                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val loading = viewModel.loading.value
                    Column{
                        SearchAppBar(
                            query = query,
                            onQueryChanged = viewModel::onQueryChanged,
                            onExecuteSearch = viewModel::newSearch,
                            scrollPosition = viewModel.categoryScrollPosition,
                            selectedCategory = selectedCategory,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                            onToggleTheme = {application.toggleTheme()}
                        )

                        Box(
                            modifier = Modifier.fillMaxSize()
                                .background(color = MaterialTheme.colors.background)
                        ){
                            if (loading){
                                LoadingRecipeListShimmer(imageHeight = 260.dp)
                            }
                            else{
                                LazyColumn{
                                    itemsIndexed(items = recipes){
                                            _, recipe ->
                                        RecipeCard(recipe = recipe, onClick = {})
                                    }
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                        }
                    }
                }
            }
        }
    }
}