package com.example.whatsappclone.presentation.upadteScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.navigation.NavHostController
import com.example.whatsappclone.Navigation.Routes
import com.example.whatsappclone.data.model.ChannelsData
import com.example.whatsappclone.data.model.StatusData
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.bottomnav.BottomNav

@Composable
fun UpdateScreen(navHostController: NavHostController) {

    val scrollstate= rememberScrollState()

    var statusdata= listOf(
        StatusData(
            name="Disha",
            image = R.drawable.disha_patani,
            time = "Just Now"
        ),
        StatusData(
            name="Mr beast",
            image = R.drawable.mrbeast,
            time = "10 min ago"
        ),
        StatusData(
            name="Disha",
            image = R.drawable.disha_patani,
            time = "Just Now"
        )
    )

    var channelsdata= listOf(
        ChannelsData(
            name = "Neat Roots",
            image = R.drawable.neat_roots,
            description = "latest in tech"
        ),
        ChannelsData(
            name = "Food Lover",
            image = R.drawable.img,
            description = "latest in tech"
        ),
        ChannelsData(
            name = "Meta",
            image = R.drawable.meta,
            description = "latest in tech"
        ),
        ChannelsData(
            name = "Neat Roots",
            image = R.drawable.neat_roots,
            description = "latest in tech"
        ),
    )
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = colorResource(R.color.lightGreen),
                modifier = Modifier.size(64.dp),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_photo_camera_24),
                    modifier = Modifier.size(40.dp),
                    contentDescription = null
                )
            }
        },
        bottomBar = {
            BottomNav(navHostController, selectedItem =0, onClick = {
                    index: Int ->
                when(index) {
                    0->{navHostController.navigate(Routes.HomeScreen)}
                    1->{navHostController.navigate(Routes.UpdateScreen)}
                    2->{navHostController.navigate(Routes.CommuntiyScreen)}
                    3->{navHostController.navigate(Routes.CallScreen)}
                }
            })
        },
        topBar = {
            TopBar()
        }
    ) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize().verticalScroll(scrollstate)
        ) {

            Text(
                text = "Status",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)

            )

            MyStatusItem()

            statusdata.forEach {
                StatusItem(statusData = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = Color.Gray)

            Text(
                text = "Channels",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            Column(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "Stay updated on topics that matter to you. find channels to follow below"
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "find channels to follow"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            channelsdata.forEach {
                ChannelItem(it)
            }
        }
    }
}