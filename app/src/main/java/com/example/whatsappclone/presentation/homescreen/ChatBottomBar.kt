package com.example.whatsappclone.presentation.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.R


@Composable
fun ChatBottomBar(
    message: String,
    onMessageChange: (String) -> Unit,
    onSend: () -> Unit,
    onAttachImage: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp)
            .imePadding(),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        IconButton(onClick = onAttachImage) {
            Icon(
                painter = painterResource(R.drawable.outline_image_24),
                contentDescription = "Select Image",
                modifier = Modifier.size(38.dp)
            )
        }

        TextField(
            value = message,
            onValueChange = onMessageChange,
            placeholder = { Text("Message") },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF0F0F0),
                unfocusedContainerColor = Color(0xFFF0F0F0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            maxLines = 4
        )

        IconButton(
            onClick = onSend,
            modifier = Modifier
                .clip(CircleShape)
                .background(colorResource(R.color.lightGreen))
                .size(48.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_send_24),
                contentDescription = "Send",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


