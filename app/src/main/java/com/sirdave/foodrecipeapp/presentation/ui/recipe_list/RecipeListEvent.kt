package com.sirdave.foodrecipeapp.presentation.ui.recipe_list

sealed class RecipeListEvent {

    object NewSearchEvent: RecipeListEvent()

    object NextPageEvent: RecipeListEvent()

    // restore state after process death
    object RestoreStateEvent: RecipeListEvent()
}
