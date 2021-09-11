package com.sirdave.foodrecipeapp.presentation.ui.recipe_list

import androidx.lifecycle.ViewModel
import com.sirdave.foodrecipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
): ViewModel(){

    init {
        println("VIEWMODEL: $repository")
        println("VIEWMODEL: $token")
    }

    fun getToken() = token
    fun getRepo() = repository

}