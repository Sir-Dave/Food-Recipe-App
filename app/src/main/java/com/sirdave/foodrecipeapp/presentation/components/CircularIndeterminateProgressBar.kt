package com.sirdave.foodrecipeapp.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
fun CircularIndeterminateProgressBar(
    isDisplayed: Boolean){
    if (isDisplayed){

        ConstraintLayout(modifier = Modifier.fillMaxSize()){
            val progressBar = createRef()
            val topGuideline = createGuidelineFromTop(0.3f)
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.constrainAs(progressBar) {
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

// hold on till I'm able to figure out its use
private fun myDecoupledConstraints(verticalBias: Float): ConstraintSet{
    return ConstraintSet {
        val topGuideline = createGuidelineFromTop(verticalBias)
        val progressBar = createRefFor("progressBar")
        val text = createRefFor("text")

        constrain(progressBar){
            top.linkTo(topGuideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(text){
            top.linkTo(progressBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}