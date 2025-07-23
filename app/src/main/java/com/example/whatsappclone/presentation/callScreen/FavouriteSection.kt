package com.example.whatsappclone.presentation.callScreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.data.model.FavouriteData
import com.example.whatsappclone.R


@Composable
@Preview(showSystemUi = true)
fun  FavouriteSection() {

    var remeberscrollstate= rememberScrollState()

    val favouriteList = listOf(
        FavouriteData(
            name = "Rashmika",
            image = R.drawable.rashmika
        ),
        FavouriteData(
            name = "Shraddha",
            image = R.drawable.sharadha_kapoor
        ),
        FavouriteData(
            name = "Tripti",
            image = R.drawable.tripti_dimri
        ),
        FavouriteData(
            name = "Shahrukh",
            image = R.drawable.sharukh_khan
        ),
        FavouriteData(
            name = "Akshay",
            image = R.drawable.akshay_kumar
        )
    )

    Column(
        modifier = Modifier.padding(
            start = 12.dp,
            bottom = 8.dp
        )
    ) {
        Text(
            text = "Favourites",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row (
            modifier = Modifier.fillMaxWidth().horizontalScroll(remeberscrollstate)
        ){
            favouriteList.forEach {
                FavoriteItem(it)
            }
        }
    }
}