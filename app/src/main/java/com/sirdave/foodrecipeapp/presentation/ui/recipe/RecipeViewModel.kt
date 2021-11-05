package com.sirdave.foodrecipeapp.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirdave.foodrecipeapp.domain.model.Recipe
import com.sirdave.foodrecipeapp.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.sirdave.foodrecipeapp.repository.RecipeRepository
import com.sirdave.foodrecipeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_RECIPE = "state.key.recipeId"

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    private val state: SavedStateHandle
): ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        state.get<Int>(STATE_KEY_RECIPE)?.let { recipeId ->
            onTriggerEvent(GetRecipeEvent(recipeId))
        }
    }

    fun onTriggerEvent(event: RecipeEvent){
        viewModelScope.launch {
            try {
                when (event){
                    is GetRecipeEvent ->{
                        if (recipe.value == null){
                            getRecipe(event.id)
                        }
                    }
                }
            }
            catch (ex: Exception){
                Log.d(TAG, "onTrigger Exception: $ex, ${ex.cause}")
            }
        }
    }

    private suspend fun getRecipe(id: Int){
        loading.value = true

        delay(1000) // simulate a delay
        val recipe = repository.get(token = token, id = id)
        this.recipe.value = recipe

        state.set(STATE_KEY_RECIPE, recipe.id)
        loading.value = false
    }
}