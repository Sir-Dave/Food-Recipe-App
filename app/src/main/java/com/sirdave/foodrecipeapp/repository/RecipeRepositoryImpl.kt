package com.sirdave.foodrecipeapp.repository

import com.sirdave.foodrecipeapp.domain.model.Recipe
import com.sirdave.foodrecipeapp.network.RecipeService
import com.sirdave.foodrecipeapp.network.model.RecipeDtoMapper

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper): RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        val recipes = recipeService.search(token, page, query).recipes
        return mapper.toDomainList(recipes)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        val recipe = recipeService.get(token, id)
        return mapper.mapToDomainModel(recipe)
    }
}