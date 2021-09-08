package com.sirdave.foodrecipeapp.repository

import com.sirdave.foodrecipeapp.domain.model.Recipe

interface RecipeRepository {
    suspend fun search(token: String, page: Int, query: String): List<Recipe>

    suspend fun get(token: String, id: Int): Recipe
}