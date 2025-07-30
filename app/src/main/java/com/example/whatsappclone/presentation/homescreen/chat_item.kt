package com.example.whatsappclone.presentation.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.whatsappclone.data.model.ChatListModel
import com.example.whatsappclone.R
import com.example.whatsappclone.data.model.ChatListModel1
import com.example.whatsappclone.data.viewModel.BaseFeatureViewModel
import com.example.whatsappclone.data.viewModel.BaseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatItem(
    chat: ChatListModel1,
    onClick: () -> Unit,
    baseViewModel: BaseFeatureViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(chat.reciverPfpUrl)
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


        Spacer(modifier = Modifier.width(12.dp))

        // Chat Info (Name, Time, Message)
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = chat.reciverName ?: "Unknown",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = formatTimestamp(chat.lastMessageTimestamp),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = chat.lastMessage ?: "No message yet",
                color = Color.DarkGray,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


fun formatTimestamp(timestamp: Long?): String {
    return if (timestamp != null && timestamp > 0) {
        val date = Date(timestamp)
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        format.format(date)
    } else {
        "--:--"
    }
}


//@Preview(showSystemUi = true)
//@Composable
//fun chatItemPrevie() {
//    ChatItem(
//        ChatListModel(
//            R.drawable.salman_khan,
//            "Salman Khan",
//            "10:00 AM",
//            "Hello"
//        )
//    )
//}