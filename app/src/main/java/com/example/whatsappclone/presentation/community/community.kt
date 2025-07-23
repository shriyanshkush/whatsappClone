package com.example.whatsappclone.presentation.community

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.data.model.channelData
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.bottomnav.BottomNav

@Composable
@Preview(showSystemUi = true)

fun Community() {

    val channelList = listOf(
        channelData(
            name = "Tech Updates",
            image = R.drawable.img,
            membercount = "12.3K"
        ),
        channelData(
            name = "Health Tips",
            image = R.drawable.meta,
            membercount = "8.7K"
        ),
        channelData(
            name = "Campus News",
            image = R.drawable.img,
            membercount = "5.2K"
        ),
        channelData(
            name = "AI Buzz",
            image = R.drawable.meta,
            membercount = "15.1K"
        ),
        channelData(
            name = "Crypto World",
            image = R.drawable.img,
            membercount = "9.6K"
        )
    )

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomNav()
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.lightGreen)
                ),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(
                    text = "Start a new Community",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your Communities",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyColumn {
                items(channelList) {
                    channelItem(channelData = it)
                }
            }
        }
    }
}