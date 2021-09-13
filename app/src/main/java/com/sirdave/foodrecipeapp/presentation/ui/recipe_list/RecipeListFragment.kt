package com.sirdave.foodrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
                Column{
                    Surface(modifier = Modifier.fillMaxWidth(),
                        elevation = 8.dp,
                        color = MaterialTheme.colors.primary){
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
                                        viewModel.newSearch(query)
                                        focusManager.clearFocus()
                                    },
                                ),
                                textStyle = TextStyle(color = MaterialTheme.colors.onSurface,
                                    background = MaterialTheme.colors.surface
                                ),
                            )
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