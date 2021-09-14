package com.sirdave.foodrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sirdave.foodrecipeapp.presentation.components.FoodCategoryChip
import com.sirdave.foodrecipeapp.util.RecipeCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel.recipes.value
                val query = viewModel.query.value
                val focusManager = LocalFocusManager.current
                val selectedCategory = viewModel.selectedCategory.value
                Column{
                    Surface(modifier = Modifier.fillMaxWidth(),
                        elevation = 8.dp,
                        color = Color.White){
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TextField(value = query, onValueChange = {newValue ->
                                    viewModel.onQueryChanged(newValue)},
                                    modifier = Modifier.fillMaxWidth(0.9f).padding(8.dp),
                                    label = {
                                        Text(text = "Search")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Search
                                    ),
                                    leadingIcon = {
                                        Icon(Icons.Filled.Search, contentDescription = "")
                                    },
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            viewModel.newSearch()
                                            focusManager.clearFocus()
                                        },
                                    ),
                                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface,
                                        background = MaterialTheme.colors.surface
                                    ),
                                )
                            }

                            LazyRow(modifier = Modifier.fillMaxWidth()
                                .padding(start = 8.dp, bottom = 8.dp)
                            ){
                                items(getAllFoodCategories()) { category ->
                                    FoodCategoryChip(category = category.value,
                                        isSelected = selectedCategory == category,
                                        onSelectedCategoryChanged = { s->
                                            viewModel.onSelectedCategoryChanged(s)
                                        },
                                        onExecuteSearch = viewModel::newSearch)
                                }
                            }
                        }
                    }
                    LazyColumn{
                        itemsIndexed(items = recipes){
                                _, recipe ->
                            RecipeCard(recipe = recipe, onClick = {})
                        }
                    }
                }
            }
        }
    }
}