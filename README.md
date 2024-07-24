<div align="center">
</br>
<img src="https://github.com/user-attachments/assets/49ccb36c-3622-4b1f-8382-ee44f9796487" width="200" />

</div>

<h1 align="center">🔒 Encrypto: A Password Manager Android Application</h1>

Encrypto is a simple Android application made using Kotlin for managing your passwords. Encrypto securely stores all your login credentials and other important information, so you never have to worry about forgetting passwords or searching for lost information. Keep your digital life organized and protected with Encrypto.

# 🤔 Purpose of this App

Many people struggle with memorizing all their passwords, and sometimes even forget their login credentials. Encrypto is built to address this issue by helping users store their passwords and other information in a secure and organized manner.

# 🧰 Features

- **Secure Storage:** Encrypto securely stores all login credentials and important information in one place, accessible only to the user. The app uses advanced encryption techniques to ensure that the data is protected from unauthorized access.

- **Easy Access:** With just a fingerprint, users can easily access all of their login information and never have to worry about forgetting passwords or searching for lost information.

- **Password Generator:** The app includes a secure password generator that can suggest stronger passwords and help users create unique and secure passwords for all their accounts.

- **User-Friendly Interface:** The app has a simple and user-friendly interface that makes it easy for users to manage their login information and passwords.

# 📱 Screenshots


![1](https://github.com/user-attachments/assets/c093a815-8f4c-4f1b-a21c-44e7147ffd1c)
<br><br>

![2](https://github.com/user-attachments/assets/d98deaa4-a911-4f51-bf6f-450c9a9acf82)
<br><br>

![3](https://github.com/user-attachments/assets/2132e18b-e35f-4060-bf3c-e167689a36eb)
<br><br>

![4](https://github.com/user-attachments/assets/bc1a7a0e-f278-4e01-bf06-55e1a4b94d74)
<br><br>

# 🛠 Made With

- [Kotlin](https://developer.android.com/kotlin/first) - First class and official programming language for Android development.
  
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - For asynchronous and more..
  
- [Android Architecture Components](https://developer.android.com/topic/architecture) - Collection of libraries that help you design testable, and maintainable apps.
  
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
    
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
    
  - [Room](https://developer.android.com/training/data-storage/room) - Room is an android library which is an ORM which wraps android's native SQLite database.
    
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - LiveData was used to save and store values for viewModel calls and response of method calls.
    
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
  
- [Password Strength Meter](https://github.com/gustavaa/AndroidPasswordStrengthMeter) - Password strength meter is an easy-to-implement and flexible password strength indicator for Android.

# 👀 Package Structure

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

# 👷‍♂️ Architecture

This app uses [MVVM(Model View View-Model)](https://developer.android.com/topic/architecture#recommended-app-arch) architecture.

![Architecture_Flow](https://user-images.githubusercontent.com/80090908/216841302-97243bc3-3df4-4416-8f1f-dc22398c86b1.png)
