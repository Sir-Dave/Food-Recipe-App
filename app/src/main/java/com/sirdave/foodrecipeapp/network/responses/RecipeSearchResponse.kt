package com.sirdave.foodrecipeapp.network.responses

import com.google.gson.annotations.SerializedName
import com.sirdave.foodrecipeapp.network.model.RecipeDto

class RecipeSearchResponse (
    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeDto>
)