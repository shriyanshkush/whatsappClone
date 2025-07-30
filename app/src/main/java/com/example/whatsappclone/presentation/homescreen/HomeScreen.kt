package com.example.whatsappclone.presentation.homescreen

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.whatsappclone.data.model.ChatListModel
import com.example.whatsappclone.R
import com.example.whatsappclone.data.viewModel.BaseFeatureViewModel
import com.example.whatsappclone.data.viewModel.BaseViewModel
import com.example.whatsappclone.presentation.bottomnav.BottomNav
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navHostController: NavHostController, homebaseViewModel: BaseFeatureViewModel) {
    var showPopUp by remember { mutableStateOf(false) }
    val chatdata by homebaseViewModel.chatList.collectAsState() // ✅ Fixed name

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    if (userId != null) {
        LaunchedEffect(userId) {
            homebaseViewModel.loadChatList() // ✅ Correct method
        }
    }

    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showPopUp = true },
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
    ) {
        Column(modifier = Modifier.padding(it).background(Color.White)) {
            Spacer(modifier = Modifier.height(8.dp))

            // ✅ Top Bar with Search & Menu
            Box(modifier = Modifier.fillMaxWidth()) {
                var isSearching by remember { mutableStateOf(false) }
                var searchText by remember { mutableStateOf("") }

                if (isSearching) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            placeholder = { Text("Search", color = Color.Gray) },
                            singleLine = true,
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth(0.8f),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                        )

                        IconButton(
                            onClick = { isSearching = false; searchText = "" },
                            modifier = Modifier.size(16.dp)
                            )
                        {
                            Icon(
                                painter = painterResource(R.drawable.cross),
                                contentDescription = null, modifier = Modifier.size(24.dp),
                            )
                        }
                    }
                } else {
                    Text(
                        text = "WhatsApp",
                        fontSize = 25.sp,
                        color = colorResource(R.color.lightGreen),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                        fontWeight = FontWeight.Bold
                    )

                    Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                        IconButton(onClick = {}) {
                            Icon(painter = painterResource(R.drawable.camera), contentDescription = null, modifier = Modifier.size(24.dp))
                        }

                        if (isSearching) {
                            IconButton(onClick = { isSearching = false; searchText = "" }) {
                                Icon(painter = painterResource(R.drawable.cross), contentDescription = null, modifier = Modifier.size(24.dp))
                            }
                        } else {
                            IconButton(onClick = { isSearching = true }) {
                                Icon(painter = painterResource(R.drawable.search), contentDescription = null, modifier = Modifier.size(24.dp))
                            }
                        }

                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(painter = painterResource(R.drawable.more), contentDescription = null, modifier = Modifier.size(24.dp))
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            DropdownMenuItem(text = { Text("New Group") }, onClick = { showMenu = false })
                            DropdownMenuItem(text = { Text("New Broadcast") }, onClick = { showMenu = false })
                            DropdownMenuItem(text = { Text("Linked Device") }, onClick = { showMenu = false })
                            DropdownMenuItem(text = { Text("Starred Message") }, onClick = { showMenu = false })
                            DropdownMenuItem(text = { Text("Settings") }, onClick = {
                                showMenu = false
                                navHostController.navigate(Routes.Settings)
                            })
                        }
                    }
                }
            }

            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            // ✅ Add User Popup
            if (showPopUp) {
                AddUserPopUop(
                    onDismis = { showPopUp = false },
                    onUserAdd = { userId, name, pfpUrl ->
                        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?:""
                        homebaseViewModel.getCurrentUserDetails(currentUserId) { myName, myProfile ->
                            homebaseViewModel.addUsertoChat(myName?:"", myProfile?:"",name?:"", userId?:"", pfpUrl?:"")
                        }

                    },
                    baseViewModel = homebaseViewModel
                )
            }

            // ✅ Chat List
            LazyColumn(
                modifier = Modifier.background(Color.White)
            ) {
                items(chatdata) { chat ->
                    ChatItem(
                        chat = chat,
                        onClick = {
                            navHostController.navigate(Routes.ChatScreen.createRoute(chat.reciverUserId ?: "",chat.reciverName?:"",chat.reciverPfpUrl?:""))
                        },
                        baseViewModel = homebaseViewModel
                    )
                }
            }
        }
    }
}
@Composable
fun AddUserPopUop(
    onDismis: () -> Unit,
    onUserAdd: (String?, String?, String?) -> Unit,
    baseViewModel: BaseFeatureViewModel
) {
    var phoneNo by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var userFound by remember { mutableStateOf<Triple<String?, String?, String?>?>(null) }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        TextField(
            value = phoneNo,
            onValueChange = { phoneNo = it },
            label = { Text("Enter Phone No.") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        Row (
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(
                onClick = {
                    isSearching = true
                    baseViewModel.searchUserByPhoneNo(phoneNo) { id, name, pfp ->
                        isSearching = false
                        userFound = if (id != null) Triple(id, name, pfp) else null
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.lightGreen))
            ) {
                Text("Search")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onDismis,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.lightGreen))
            ) {
                Text("Cancel")
            }
        }

        when {
            isSearching -> Text("Searching...", color = Color.Gray)
            userFound != null -> {
                Column {
                    Text("User Found: ${userFound!!.second}")
                    Button(
                        onClick = {
                            onUserAdd(userFound!!.first, userFound!!.second, userFound!!.third)
                            onDismis()
                        },
                        modifier = Modifier.height(40.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.lightGreen))
                    ) {
                        Text("Add user to Chat")
                    }
                }
            }
            else -> Text("No user found", color = Color.Gray)
        }
    }
}

