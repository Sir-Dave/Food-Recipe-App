package com.sirdave.foodrecipeapp.presentation.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sirdave.foodrecipeapp.presentation.BaseApplication
import com.sirdave.foodrecipeapp.presentation.components.*
import com.sirdave.foodrecipeapp.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.sirdave.foodrecipeapp.ui.FoodRecipeAppTheme
import com.sirdave.foodrecipeapp.util.SnackbarController
import com.sirdave.foodrecipeapp.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment: Fragment() {

    @Inject
    lateinit var application: BaseApplication
    private val snackbarController = SnackbarController(lifecycleScope)

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { recipeId ->
            viewModel.onTriggerEvent(GetRecipeEvent(recipeId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value

                var scaffoldState = rememberScaffoldState()
                FoodRecipeAppTheme(darkTheme = application.isDark.value) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ){
                        Box(modifier = Modifier.fillMaxSize()){
                            if (loading && recipe == null){
                                LoadingRecipeShimmer(imageHeight = IMAGE_HEIGHT.dp)
                            }
                            else{
                                recipe?.let {
                                    RecipeView(recipe = it)
                                }
                            }
                            
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                            DefaultSnackBar(
                                snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                },
                            modifier = Modifier.align(Alignment.BottomCenter))
                        }
                    }
                }
            }
        }
    }
}