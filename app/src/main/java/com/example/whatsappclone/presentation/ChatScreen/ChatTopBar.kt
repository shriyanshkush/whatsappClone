package com.example.whatsappclone.presentation.ChatScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.whatsappclone.R
import okhttp3.internal.wait

@Composable

fun ChatTopBar(navHostController: NavHostController,name:String,pfpUrl:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth().background(Color.White)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left Section: Back Icon + Profile + Name
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = {
                    navHostController.popBackStack()
                },
                modifier = Modifier
                    .size(28.dp)
                    .padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back",
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pfpUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile Image",
                placeholder = painterResource(R.drawable.profile_placeholder),
                error = painterResource(R.drawable.profile_placeholder),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Online",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }
        }

        // Right Section: Action Icons
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.video_svgrepo_com),
                contentDescription = "Video Call",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(R.drawable.outline_phone_24),
                contentDescription = "Voice Call",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(R.drawable.more),
                contentDescription = "More Options",
                modifier = Modifier.size(24.dp)
            )
        }

    }
}
