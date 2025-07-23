package com.example.whatsappclone.presentation.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.data.model.ChatListModel
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.bottomnav.BottomNav

@Composable
@Preview(showSystemUi = true)
fun HomeScreen() {

    val chatdata= listOf(
        ChatListModel(
            R.drawable.salman_khan,
            "Salman Khan",
            "10:00 AM",
            "Hello"
        ),
        ChatListModel(
            R.drawable.rashmika,
            "Rashmika",
            "10:00 AM",
            "Hi"
        ),
        ChatListModel(
            R.drawable.tripti_dimri,
            "Tripti",
            "10:00 AM",
            "Hello"
        ),
        ChatListModel(
            R.drawable.sharukh_khan,
            "Shahruk Khan",
            "10:00 AM",
            "Hello"
        ),
        ChatListModel(
            R.drawable.sharadha_kapoor,
            "Shradha Kapoor",
            "10:00 AM",
            "Hello"
        ),
        ChatListModel(
            R.drawable.akshay_kumar,
            "Akshay Kumar",
            "10:00 AM",
            "Hello"
        ),
        ChatListModel(
            R.drawable.ajay_devgn,
            "Ajay Devgan",
            "10:00 AM",
            "Hello"
        ),
        ChatListModel(
            R.drawable.salman_khan,
            "Salman Khan",
            "10:00 AM",
            "Hello"
        ),
        ChatListModel(
            R.drawable.rashmika,
            "Rashmika",
            "10:00 AM",
            "Hi"
        ),
        ChatListModel(
            R.drawable.tripti_dimri,
            "Tripti",
            "10:00 AM",
            "Hello"
        ),
        ChatListModel(
            R.drawable.sharukh_khan,
            "Shahruk Khan",
            "10:00 AM",
            "Hello"
        ),
        ChatListModel(
            R.drawable.sharadha_kapoor,
            "Shradha Kapoor",
            "10:00 AM",
            "Hello"
        ),
        ChatListModel(
            R.drawable.akshay_kumar,
            "Akshay Kumar",
            "10:00 AM",
            "Hello"
        ),
        ChatListModel(
            R.drawable.ajay_devgn,
            "Ajay Devgan",
            "10:00 AM",
            "Hello"
        ),
    )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                modifier = Modifier.size(64.dp),
                containerColor = colorResource(R.color.lightGreen),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(R.drawable.chat_icon),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                )
            }
        },
        bottomBar = {
            BottomNav()
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "WhatsApp",
                    fontSize = 28.sp,
                    color = colorResource(R.color.lightGreen),
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp),
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.camera),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.more),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            HorizontalDivider()

            LazyColumn {
                items(chatdata) {
                    ChatItem(
                        chatListModel = it
                    )
                }
            }
        }
    }
}