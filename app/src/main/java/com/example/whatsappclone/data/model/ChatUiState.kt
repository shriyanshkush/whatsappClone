package com.example.whatsappclone.data.model

sealed class ChatUiState {
    object Loading : ChatUiState()
    data class Success(val messages: List<Message1>) : ChatUiState()
    object Empty : ChatUiState()
    data class Error(val message: String) : ChatUiState()
}
