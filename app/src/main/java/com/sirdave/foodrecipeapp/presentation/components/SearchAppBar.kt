package com.sirdave.foodrecipeapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sirdave.foodrecipeapp.presentation.ui.recipe_list.FoodCategory
import com.sirdave.foodrecipeapp.presentation.ui.recipe_list.getAllFoodCategories
import kotlinx.coroutines.launch


@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    scrollPosition: Int,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangeCategoryScrollPosition: (Int) -> Unit,
    onToggleTheme: () -> Unit){

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()


    Surface(modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        color = MaterialTheme.colors.surface){
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(value = query, onValueChange = {newValue ->
                    onQueryChanged(newValue)},
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
                            onExecuteSearch()
                            focusManager.clearFocus()
                        },
                    ),
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface,
                        background = MaterialTheme.colors.surface
                    ),
                )

                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)) {
                    val menu = createRef()
                    IconButton(onClick = onToggleTheme,
                        modifier = Modifier.constrainAs(menu){
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            top.linkTo(parent.top)
                        }
                    ) {
                        Icon(Icons.Filled.MoreVert, contentDescription = null)
                    }

                }
            }

            LazyRow(modifier = Modifier.fillMaxWidth()
                .padding(start = 8.dp, bottom = 8.dp)
                //.horizontalScroll(scrollState)
            ){
                scope.launch {
                    scrollState.scrollTo(scrollPosition)
                }
                items(getAllFoodCategories()) { category ->
                    FoodCategoryChip(category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = { s->
                            onSelectedCategoryChanged(s)
                            onChangeCategoryScrollPosition(scrollState.value)
                        },
                        onExecuteSearch = { onExecuteSearch() })
                }
            }
        }
    }
}