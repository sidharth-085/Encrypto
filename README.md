
# 🔒 Encrypto: A Password Manager Android Application

Encrypto is a simple Android application made using Kotlin for managing your passwords. Encrypto securely stores all your login credentials and other important information, so you never have to worry about forgetting passwords or searching for lost information. Keep your digital life organized and protected with Encrypto.

# 🤔 Purpose of this App

Many people struggle with memorizing all their passwords, and sometimes even forget their login credentials. Encrypto is built to address this issue by helping users store their passwords and other information in a secure and organized manner.

# 🧰 Features

- **Secure Storage:** Encrypto securely stores all login credentials and important information in one place, accessible only to the user. The app uses advanced encryption techniques to ensure that the data is protected from unauthorized access.

- **Easy Access:** With just a fingerprint, users can easily access all of their login information and never have to worry about forgetting passwords or searching for lost information.

- **Password Generator:** The app includes a secure password generator that can suggest stronger passwords and help users create unique and secure passwords for all their accounts.

- **User-Friendly Interface:** The app has a simple and user-friendly interface that makes it easy for users to manage their login information and passwords.

# 📱 Screenshots


# 🛠 Made With

- Kotlin: First-class and official programming language for Android development.

- Coroutines: For asynchronous programming.

- Android Architecture Components: Collection of libraries that help you design testable and maintainable apps.

  - **ViewModel:** Stores UI-related data that isn't destroyed on UI changes.

  - **ViewBinding:** Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.

  - **Room:** ORM library that wraps Android's native SQLite database.

  - **LiveData:** Used to save and store values for ViewModel calls and response of method calls.

- Material Components for Android: Modular and customizable Material Design UI components for Android.

- Password Strength Meter: Easy-to-implement and flexible password strength indicator for Android.

# Package Structure

    com.sid.encrypto    # Root Package
    
    ├── data                # For data handling.
    |   ├── model           # Model data classes, for local entities.
    │   ├── repository      # Single source of data.
    │   └── room            # For saving data.
    |
    ├── adapter             # All Adapters for recyclerViews              
    │ 
    |── service             # Notification Service
    |
    ├── ui                  # UI/View layer
    |   ├── auth            # For Security
    |   └── fragments       # All fragments     
    │   └── splashScreen    # SplashScreen
    |
    ├── utils               # Utility Classes / Kotlin extensions
    |
    └── viewmodel           # Generates a binding class for each XML layout file

# Architecture

This app uses [MVVM(Model View View-Model)](https://developer.android.com/topic/architecture#recommended-app-arch) architecture.

![Architecture_Flow](https://user-images.githubusercontent.com/80090908/216841302-97243bc3-3df4-4416-8f1f-dc22398c86b1.png)
