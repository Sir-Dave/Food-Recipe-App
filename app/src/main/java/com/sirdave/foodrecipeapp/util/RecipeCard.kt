package com.sirdave.foodrecipeapp.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sirdave.foodrecipeapp.R
import com.sirdave.foodrecipeapp.domain.model.Recipe

@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit){
    Card(modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
            elevation = 8.dp
    ){
        Column{
            recipe.sourceUrl?.let {
                Image(painter = painterResource(id = R.drawable.empty_plate),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(225.dp))

                recipe.title?.let {title->
                    Row(modifier = Modifier.fillMaxWidth().padding(
                            top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                    ){
                        Text(
                                text = title,
                                modifier = Modifier.fillMaxWidth(0.85f)
                                        .wrapContentWidth(Alignment.Start),
                                style = MaterialTheme.typography.h5
                        )

                        Text(
                                text = recipe.rating.toString(),
                                modifier = Modifier.fillMaxWidth()
                                        .wrapContentWidth(Alignment.End)
                                        .align(Alignment.CenterVertically),
                                style = MaterialTheme.typography.h6
                        )

                    }
                }
            }
        }
    }

}
