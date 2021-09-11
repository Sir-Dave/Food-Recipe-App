package com.sirdave.foodrecipeapp.di

import com.sirdave.foodrecipeapp.network.RecipeService
import com.sirdave.foodrecipeapp.network.model.RecipeDtoMapper
import com.sirdave.foodrecipeapp.repository.RecipeRepository
import com.sirdave.foodrecipeapp.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(recipeService: RecipeService,
                                recipeDtoMapper: RecipeDtoMapper):
            RecipeRepository{
        return RecipeRepositoryImpl(recipeService, recipeDtoMapper)
    }
}