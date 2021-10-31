package com.sirdave.foodrecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirdave.foodrecipeapp.domain.model.Recipe
import com.sirdave.foodrecipeapp.repository.RecipeRepository
import com.sirdave.foodrecipeapp.util.TAG
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val loading = mutableStateOf(false)

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Int = 0

    val page = mutableStateOf(1)
    var recipeListScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPage(p)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { position ->
            setListScrollPosition(position)
        }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
            setSelectedCategory(c)
        }
        if (recipeListScrollPosition != 0)
            RecipeListEvent.RestoreStateEvent
        else
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

                    is RecipeListEvent.RestoreStateEvent ->{
                        restoreState()
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
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)){
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

    private suspend fun restoreState(){
        loading.value = true
        val results = mutableListOf<Recipe>()
        for (p in 1..page.value){
            val result = repository.search(
                token = token,
                page = p,
                query = query.value
            )
            results.addAll(result)
            if (p == page.value){
                recipes.value = results
                loading.value = false
            }
        }
    }

    private fun appendRecipes(recipes: List<Recipe>){
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    fun incrementPage(){
        setPage(page.value + 1)
    }

    fun onChangeRecipeScrollPosition(position: Int){
        setListScrollPosition(position)
    }

    private fun resetSearchState(){
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory(){
        setSelectedCategory(null)
    }

    fun onQueryChanged(query: String){
        setQuery(query)
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Int){
        categoryScrollPosition = position
    }

    fun setListScrollPosition(position: Int){
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    fun setPage(page: Int){
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    fun setQuery(query: String){
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }

    fun setSelectedCategory(category: FoodCategory?){
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

}