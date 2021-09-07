package com.sirdave.foodrecipeapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    val id: String? = null,
    val title: String? = null,
    val publisher: String? = null,
    val featuredImage: String? = null,
    val rating: Int? = 0,
    val sourceUrl: String? = null,
    val descriptions: String? = null,
    val cookingInstructions: String? = null,
    val ingredients: List<String>? = listOf(),
    val dateAdded: String? = null,
    val dateUpdated: String? = null,
) : Parcelable