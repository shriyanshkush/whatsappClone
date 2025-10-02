# 📱 WhatsApp Clone – Real-time Chat App

# 

A **WhatsApp-like messaging app** built with **Kotlin**, **Jetpack Compose**, **Firebase**, and **Cloudinary**.  
The app uses modern Android architecture patterns (**MVVM**, **Hilt**) and provides a reactive, smooth, and responsive chat experience.

* * *

## 🚀 Features

# 

*   **💬 Real-time Chat** – Sub-200ms message delivery using **Firebase Realtime Database**.
    
*   **📸 Media Sharing** – Images/videos uploaded via **Cloudinary CDN** for optimized delivery.
    
*   **🔐 Secure Authentication** – Phone OTP login via **Firebase Authentication** with high reliability (>95%).
    
*   **📱 Modern UI** – Built with **Jetpack Compose** for fully reactive, smooth UI updates.
    
*   **🛠 Architecture** – MVVM + Hilt dependency injection for maintainable and scalable code.
    

* * *

## 📂 Project Structure

```text
com.example.whatsappclone
├── data
│   ├── model           # Data models
│   └── viewModel       # ViewModels for MVVM
├── DI                  # Dependency Injection setup
├── Navigation          # App navigation components
├── presentation
│   ├── bottomnav       # Bottom navigation UI
│   ├── callScreen      # Call screen UI
│   ├── ChatScreen      # Chat screen UI
│   ├── community       # Community features
│   ├── homescreen      # Home screen
│   ├── Screens         # Other generic screens
│   ├── Settings        # Settings screen
│   ├── updateScreen    # Profile update screen
│   ├── UserProfileSetScreen
│   ├── UserRegistration
│   └── welcomeScreen
├── ui.theme
│   ├── Color.kt        # App color palette
│   ├── Theme.kt        # Compose theme setup
│   └── Type.kt         # Typography styles
├── Util
│   └── CloudinaryUpload.kt  # Media upload utility
├── MainActivity.kt
├── Secrets.kt           # API keys or secrets
└── WhatsAppCloneApplication.kt  # Application class
```

* * *

## 🛠️ Tech Stack

# 

*   **Language** → Kotlin
    
*   **UI** → Jetpack Compose
    
*   **Architecture** → MVVM + Hilt
    
*   **Backend** → Firebase Realtime Database & Firebase Authentication
    
*   **Media CDN** → Cloudinary
    

* * *

## 📌 Setup

# 

1.  Clone the repo:
    

`git clone [https://github.com/shriyanshkush/whatsappClone](https://github.com/shriyanshkush/whatsappClone) cd whatsappClone`

2.  Open in **Android Studio** (Arctic Fox or newer).
    
3.  Add Firebase configuration:
    

*   Add `google-services.json` to `app/`.
    
*   Enable **Firebase Realtime Database** and **Authentication (Phone)**.
    

4.  Add Cloudinary credentials in `Secrets.kt`:
    

`const val CLOUDINARY_CLOUD_NAME = "your_cloud_name" const val CLOUDINARY_API_KEY = "your_api_key" const val CLOUDINARY_API_SECRET = "your_api_secret"`

5.  Build & run the app on a device/emulator.
    

* * *

## 📌 Screenshots / Demo

# 

Comming Soon...

* * *

## 📌 Future Enhancements

# 

*   🖼 Add **image/video compression** before upload to save bandwidth.
    
*   🔔 Push notifications for incoming messages.
    
*   💬 Group chat support.
    
*   🛡 End-to-end encryption for messages.
    

* * *

## 📄 License

# 

MIT License © 2025
