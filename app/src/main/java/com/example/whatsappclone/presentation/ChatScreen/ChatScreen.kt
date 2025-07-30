package com.example.whatsappclone.presentation.ChatScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.whatsappclone.data.model.ChatUiState
import com.example.whatsappclone.data.model.Message1
import com.example.whatsappclone.data.viewModel.BaseFeatureViewModel
import com.example.whatsappclone.presentation.homescreen.ChatBottomBar
import com.example.whatsappclone.util.CloudinaryUploader
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ChatScreen(
    chatId: String,
    userName: String,
    profileUrl: String,
    viewModel: BaseFeatureViewModel,
    navController: NavHostController
){
    val uiState by viewModel.chatUiState.collectAsState()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    var messageText by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf<String?>(null) }

    val context= LocalContext.current

    // Image Picker Launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            CloudinaryUploader.uploadImage(
                context = context,
                imageUri = it,
                folderPath = "chats/$chatId",
                fileName = "image_${System.currentTimeMillis()}.jpg"
            ) { uploadedUrl ->
                if (uploadedUrl != null) {
                    imageUrl = uploadedUrl
                }
            }
        }
    }


    LaunchedEffect(chatId) {
        viewModel.observeMessages(chatId)
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = { ChatTopBar(navHostController = navController, name = userName, pfpUrl = profileUrl) },
        bottomBar = {
            ChatBottomBar(
                message = messageText,
                onMessageChange = { messageText = it },
                onSend = {
                    if (messageText.isBlank() && imageUrl.isNullOrBlank()) return@ChatBottomBar

                    val message = Message1(
                        messageId = "",
                        messageText = messageText,
                        senderId = currentUserId,
                        timestamp = System.currentTimeMillis(),
                        type = if (!imageUrl.isNullOrBlank()) "media" else "text",
                        mediaUrl = imageUrl,
                        mediaType = if (!imageUrl.isNullOrBlank()) "image" else null
                    )

                    val receiverId = chatId.split("_").firstOrNull { it != currentUserId } ?: return@ChatBottomBar
                    viewModel.sendMessage(receiverId, message)

                    // clear fields
                    messageText = ""
                    imageUrl = null
                },
                onAttachImage = {
                    imagePickerLauncher.launch("image/*")
                }
            )
        }

    ) { paddingValues ->
        when (uiState) {
            is ChatUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ChatUiState.Empty -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No messages yet", color = Color.Gray)
                }
            }

            is ChatUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${(uiState as ChatUiState.Error).message}", color = Color.Red)
                }
            }

            is ChatUiState.Success -> {
                val messages = (uiState as ChatUiState.Success).messages
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .background(Color(0xFFF5F5F5))
                        .padding(paddingValues)
                ) {
                    items(messages) { message ->
                        MessageBubble(message = message, isOutgoing = message.senderId == currentUserId)
                    }
                }
            }
        }
    }
}

