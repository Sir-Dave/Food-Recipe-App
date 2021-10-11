package com.sirdave.foodrecipeapp.ui

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.sp
import com.sirdave.foodrecipeapp.R


val QuickSand = FontFamily(
    Font(R.font.quicksand_light, FontWeight.W300),
    Font(R.font.quicksand_regular, FontWeight.W400),
    Font(R.font.quicksand_medium, FontWeight.W500),
    Font(R.font.quicksand_bold, FontWeight.W600)
)

val QuickSandTypography = Typography(
    h1 = TextStyle(
        fontSize = 30.sp,
        fontFamily = QuickSand,
        fontWeight = FontWeight.W500
    ),

    h2 = TextStyle(
        fontSize = 24.sp,
        fontFamily = QuickSand,
        fontWeight = FontWeight.W500
    ),

    h3 = TextStyle(
        fontSize = 20.sp,
        fontFamily = QuickSand,
        fontWeight = FontWeight.W500
    ),

    h4 = TextStyle(
        fontSize = 18.sp,
        fontFamily = QuickSand,
        fontWeight = FontWeight.W400
    ),

    h5 = TextStyle(
        fontSize = 16.sp,
        fontFamily = QuickSand,
        fontWeight = FontWeight.W400
    ),

    h6 = TextStyle(
        fontSize = 14.sp,
        fontFamily = QuickSand,
        fontWeight = FontWeight.W400
    ),

    subtitle1 = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
    ),
    body1 = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = QuickSand,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        color = Color.White
    ),
    caption = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    )
)

// Set of Material typography styles to start with
val typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)