package com.sirdave.foodrecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirdave.foodrecipeapp.domain.model.Recipe
import com.sirdave.foodrecipeapp.repository.RecipeRepository
import com.sirdave.foodrecipeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
): ViewModel(){

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val loading = mutableStateOf(false)

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Int = 0

    val page = mutableStateOf(1)
    var recipeListSCrollPosition = 0

    init {
        onTriggerEvent(RecipeListEvent.NewSearchEvent)
    }

    fun onTriggerEvent(event: RecipeListEvent){
        viewModelScope.launch {
            try {
                when (event){
                    is RecipeListEvent.NewSearchEvent ->{
                        newSearch()
                    }

                    is RecipeListEvent.NextPageEvent ->{
                        nextPage()
                    }
                }
            }
            catch (e: Exception){
                Log.d(TAG, "Exception: $e ==== ${e.cause}")
            }
        }
    }

    private suspend fun newSearch(){
        loading.value = true

        resetSearchState()

        delay(3000)

        val results = repository.search(
            token = token,
            page = 1,
            query = query.value
        )
        recipes.value = results
        loading.value = false
    }

    private suspend fun nextPage(){
        if ((recipeListSCrollPosition + 1) >= (page.value * PAGE_SIZE)){
            loading.value = true
            incrementPage()

            Log.d(TAG, "next page ${page.value}")
            delay(1000)

            if (page.value > 1){
                val results = repository.search(
                    token = token,
                    page = page.value,
                    query = query.value
                )
                appendRecipes(results)
            }
            loading.value = false
        }
    }

    private fun appendRecipes(recipes: List<Recipe>){
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    fun incrementPage(){
        page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int){
        recipeListSCrollPosition = position
    }

    private fun resetSearchState(){
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory(){
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String){
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Int){
        categoryScrollPosition = position
    }

}