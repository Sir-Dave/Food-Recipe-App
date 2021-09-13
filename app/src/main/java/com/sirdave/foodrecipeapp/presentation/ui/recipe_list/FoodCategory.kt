package com.sirdave.foodrecipeapp.presentation.ui.recipe_list

import com.sirdave.foodrecipeapp.presentation.ui.recipe_list.FoodCategory.*

enum class FoodCategory(val value: String) {
    CHICKEN("chicken"),
    BEEF("beef"),
    SOUP("soup"),
    DESSERT("dessert"),
    VEGETARIAN("vegetarian"),
    MILK("milk"),
    VEGAN("vegan"),
    PIZZA("pizza"),
    DONUT("donut")
}

fun getAllFoodCategories(): List<FoodCategory>{
    return listOf(CHICKEN, BEEF, SOUP, DESSERT,
        VEGAN, VEGETARIAN, MILK, PIZZA, DONUT
    )
}

fun getFoodCategory(category: String): FoodCategory?{
    val map = FoodCategory.values().associateBy { it.value }
    return map[category]
}