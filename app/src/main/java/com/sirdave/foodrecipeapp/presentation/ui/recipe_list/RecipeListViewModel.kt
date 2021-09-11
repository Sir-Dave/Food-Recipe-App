package com.sirdave.foodrecipeapp.presentation.ui.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirdave.foodrecipeapp.domain.model.Recipe
import com.sirdave.foodrecipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
): ViewModel(){

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")

    init {
        newSearch()
    }

    fun newSearch(){
        viewModelScope.launch {
            val results = repository.search(
                token = token,
                page = 1,
                query = "chicken"
            )
            recipes.value = results
        }
    }

    fun onQueryChanged(query: String){
        this.query.value = query
    }

}