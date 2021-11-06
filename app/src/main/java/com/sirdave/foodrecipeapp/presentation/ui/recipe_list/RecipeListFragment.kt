package com.sirdave.foodrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.sirdave.foodrecipeapp.presentation.BaseApplication
import com.sirdave.foodrecipeapp.presentation.components.RecipeList
import com.sirdave.foodrecipeapp.presentation.components.SearchAppBar
import com.sirdave.foodrecipeapp.ui.FoodRecipeAppTheme
import com.sirdave.foodrecipeapp.util.SnackbarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()
    private val snackbarController = SnackbarController(lifecycleScope)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel.recipes.value
                val query = viewModel.query.value
                val selectedCategory = viewModel.selectedCategory.value
                val page = viewModel.page.value
                val loading = viewModel.loading.value
                val scaffoldState = rememberScaffoldState()

                 FoodRecipeAppTheme(
                     darkTheme = application.isDark.value,
                     displayProgressBar = loading,
                     scaffoldState = scaffoldState
                 ) {
                    
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
                                    viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
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
                        RecipeList(
                            loading = loading,
                            recipes = recipes,
                            onChangeRecipeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                            page = page,
                            onNextPage = {
                                viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent)
                                             },
                            scaffoldState = scaffoldState,
                            snackbarController = snackbarController,
                            navController = findNavController()
                        )
                    }
                }
            }
        }
    }
}