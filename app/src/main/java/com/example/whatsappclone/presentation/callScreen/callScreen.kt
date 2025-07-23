package com.example.whatsappclone.presentation.callScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.data.model.Call
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.bottomnav.BottomNav

@Composable
@Preview(showSystemUi = true)
fun CallScreen() {

    val callList = listOf(
        Call(
            image = R.drawable.salman_khan, // Replace with actual drawable resource
            name = "Amit Sharma",
            time = "Today, 10:30 AM",
            isMissed = false
        ),
        Call(
            image = R.drawable.sharukh_khan,
            name = "Sneha Verma",
            time = "Yesterday, 5:45 PM",
            isMissed = true
        ),
        Call(
            image = R.drawable.rashmika,
            name = "Rahul Mehta",
            time = "Monday, 9:15 AM",
            isMissed = false
        ),
        Call(
            image = R.drawable.tripti_dimri,
            name = "Priya Singh",
            time = "Sunday, 11:00 AM",
            isMissed = true
        ),
        Call(
            image = R.drawable.sharadha_kapoor,
            name = "Vikas Rao",
            time = "Saturday, 2:20 PM",
            isMissed = false
        ),
        Call(
            image = R.drawable.ajay_devgn,
            name = "Anjali Patil",
            time = "Friday, 8:05 PM",
            isMissed = true
        )
    )


    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomNav()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = colorResource(R.color.lightGreen),
                modifier = Modifier.size(64.dp),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_call),
                    modifier = Modifier.size(30.dp),
                    contentDescription = null
                )
            }
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            Spacer(modifier = Modifier.height(12.dp))
            FavouriteSection()

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.lightGreen)
                ),
                onClick = {

                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(
                    text = "Start a new Call",
                    fontSize = 16.sp
                )
            }

            Text(
                text = "Recent Calls",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyColumn {
                items(callList){
                    CallItem(call = it)
                }
            }
        }
    }
}