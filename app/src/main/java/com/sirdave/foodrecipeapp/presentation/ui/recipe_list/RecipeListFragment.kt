package com.sirdave.foodrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sirdave.foodrecipeapp.presentation.BaseApplication
import com.sirdave.foodrecipeapp.presentation.components.*
import com.sirdave.foodrecipeapp.ui.FoodRecipeAppTheme
import com.sirdave.foodrecipeapp.util.SnackbarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    val viewModel: RecipeListViewModel by viewModels()
    val snackbarController = SnackbarController(lifecycleScope)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                 FoodRecipeAppTheme(darkTheme = application.isDark.value) {

                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val loading = viewModel.loading.value
                    val scaffoldState = rememberScaffoldState()
                    
                    Scaffold(topBar = {
                        SearchAppBar(
                            query = query,
                            onQueryChanged = viewModel::onQueryChanged,
                            onExecuteSearch = {
                                if (viewModel.selectedCategory.value?.value == "Milk"){
                                    snackbarController.getScope().launch {
                                        snackbarController.showSnackBar(
                                            scaffoldState = scaffoldState,
                                            message = "Invalid category: MILK",
                                            actionLabel = "Dismiss"
                                        )
                                    }
                                }
                                else{
                                    viewModel.newSearch()
                                }
                            },
                            scrollPosition = viewModel.categoryScrollPosition,
                            selectedCategory = selectedCategory,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                            onToggleTheme = {application.toggleTheme()}
                        )
                    },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }

                        ) {
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
                            DefaultSnackBar(
                                snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() },
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}