package com.sirdave.foodrecipeapp.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sirdave.foodrecipeapp.R
import com.sirdave.foodrecipeapp.domain.model.Recipe
import com.sirdave.foodrecipeapp.presentation.ui.recipe_list.PAGE_SIZE
import com.sirdave.foodrecipeapp.presentation.ui.recipe_list.RecipeListEvent
import com.sirdave.foodrecipeapp.util.SnackbarController
import kotlinx.coroutines.launch

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    onNextPage: (RecipeListEvent) -> Unit,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,
    navController: NavController
){
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ){
        if (loading && recipes.isEmpty()){
            LoadingRecipeListShimmer(imageHeight = 260.dp)
        }
        else{
            LazyColumn{
                itemsIndexed(items = recipes){
                        index, recipe ->
                    onChangeRecipeScrollPosition(index)
                    if ((index + 1) >= page * PAGE_SIZE && !loading){
                        onNextPage(RecipeListEvent.NextPageEvent)
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            if (recipe.id != null){
                                val bundle = Bundle()
                                bundle.putInt("recipeId", recipe.id)
                                navController.navigate(R.id.viewRecipe, bundle)

                            }
                            else{
                                snackbarController.getScope().launch {
                                    snackbarController.showSnackBar(
                                        scaffoldState = scaffoldState,
                                        message = "Recipe Error",
                                        actionLabel = "Ok"
                                    )
                                }
                            }
                        })
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