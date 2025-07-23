package com.example.whatsappclone.presentation.callScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.data.model.Call
import com.example.whatsappclone.R

@Composable


fun CallItem(call: Call) {

    var missed by remember {
        mutableStateOf(true)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {
        Image(
            painter = painterResource(call.image),
            contentDescription = null,
            modifier = Modifier.size(64.dp).clip(
                CircleShape
            ),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
        ) {
            Text(
                text =call.name,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_call_missed_24),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = if(call.isMissed) {
                        Color.Red
                    } else{
                        colorResource(R.color.lightGreen)
                    }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = call.time,
                    fontSize = 16.sp,
                    color = Color.Gray
                )

            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            IconButton(
                onClick = {},
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_phone_24),
                    contentDescription = null,
                )
            }
        }

    }
}