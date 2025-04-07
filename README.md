# YahApp - Professional Job Search Platform 🚀

[![Android CI/CD](https://github.com/DGOSalazar/yahApp/actions/workflows/android.yml/badge.svg)](https://github.com/DGOSalazar/yahApp/actions/workflows/android.yml)

<div align="center">
  <img width="292" alt="image" src="https://github.com/user-attachments/assets/97af0d84-6a84-4e02-ae30-c6f01b9a574b" />

  <h3>Modern Android Job Search Platform with AI Integration</h3>
</div>

## Downloads 📥

You can download the latest version of YahApp from our [Releases](https://github.com/DGOSalazar/yahApp/releases) page. Each release includes:
- Debug APK for testing
- Release notes with changes and improvements
- Automatic builds via GitHub Actions

## Overview 📱

YahApp is a comprehensive Android application designed to revolutionize the job search experience by leveraging modern technologies and AI capabilities. Built with Clean Architecture principles and the latest Android development practices.

<details>
<summary>🏗️ Architecture</summary>

### Clean Architecture Implementation

```
com.cortech.yahapp/
├── core/                          # Core functionality shared across features
│   ├── data/                      # Data layer implementation
│   │   ├── api/                   # API interfaces and services
│   │   ├── local/                 # Local storage implementations
│   │   ├── model/                 # Data models and DTOs
│   │   │   ├── auth/              # Authentication models
│   │   │   └── recommendation/    # Recommendation models
│   │   └── repository/            # Repository implementations
│   ├── di/                        # Dependency injection modules
│   ├── domain/                    # Business logic & use cases
│   │   ├── model/                 # Domain models
│   │   │   ├── auth/              # Authentication domain models
│   │   │   └── chat/              # Chat domain models
│   │   ├── repository/            # Repository interfaces
│   │   └── usecase/               # Use cases by feature
│   │       ├── auth/              # Authentication use cases
│   │       ├── chat/              # Chat use cases
│   │       └── profile/           # Profile use cases
│   ├── navigation/                # Navigation components
│   ├── presentation/              # Shared UI components
│   │   ├── components/            # Reusable composables
│   │   └── theme/                 # App theming
│   └── utils/                     # Utility classes
└── features/                      # Feature modules
    ├── home/                      # Home feature
    │   ├── model/                 # Feature-specific models
    │   │   └── state/             # UI state management
    │   ├── screen/                # UI screens
    │   │   └── component/         # Screen-specific components
    │   └── viewmodel/             # ViewModels
    ├── profile/                   # Profile management
    │   ├── model/                 # Profile models
    │   ├── view/                  # Profile UI
    │   │   └── component/         # Profile components
    │   └── viewmodel/             # Profile ViewModels
    ├── register/                  # User registration
    │   ├── model/                 # Registration models
    │   ├── view/                  # Registration UI
    │   └── viewmodel/             # Registration ViewModels
    └── splash/                    # App initialization
        ├── model/                 # Splash models
        ├── view/                  # Splash screen
        └── viewmodel/             # Splash ViewModels
```

### Architecture Flow Diagram

```mermaid
graph TD
    subgraph Presentation Layer
        UI[UI Components] --> |State & Events| VM[ViewModels]
        VM --> |UI State| UI
    end
    
    subgraph Domain Layer
        VM --> |Execute| UC[Use Cases]
        UC --> |Domain Models| VM
        UC --> |Repository Interface| R[Repositories]
    end
    
    subgraph Data Layer
        R --> |Implementation| RI[Repository Impl]
        RI --> |DTOs| API[API Service]
        RI --> |Local Data| LS[Local Storage]
        API --> |Network| BE[Backend Services]
        BE --> |Response| API
        API --> |Mapped Models| RI
        LS --> |Cached Data| RI
    end
    
    style Presentation Layer fill:#e1f5fe,stroke:#01579b
    style Domain Layer fill:#f3e5f5,stroke:#4a148c
    style Data Layer fill:#e8f5e9,stroke:#1b5e20
```
</details>

<details>
<summary>🌐 API Integration</summary>

### External Services

1. **User Authentication & Profile API**
   ```
   Base URL: https://hackaton-rails-api.duckdns.org:3000
   Endpoints:
   - GET /users/?name={user_name}
   - POST /users/
   ```

2. **Job Positions API**
   ```
   Base URL: https://api-ai-solution.vercel.app
   Endpoints:
   - GET /jobs/recommended
   - POST /jobs/position
   ```

3. **Google Gemini AI Integration**
   - CV Analysis
   - Job Recommendations
   - Natural Language Chat

</details>

<details>
<summary>🛠️ Technical Stack</summary>

### Core Technologies

- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Dependency Injection**: Hilt
- **Networking**: Retrofit 2.9.0 + OkHttp 4.12.0
- **JSON Parsing**: Gson 2.10.1
- **Async Operations**: Kotlin Coroutines & Flow
- **Local Storage**: SharedPreferences
- **AI Integration**: Google Gemini

### Key Features

- Material 3 Design
- Dark/Light Theme Support
- PDF Processing
- Real-time Chat
- AI-powered Job Matching
- Profile Management
- Secure Authentication

</details>

<details>
<summary>🔧 Setup & Configuration</summary>

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17
- Android SDK 34
- Kotlin 1.9.0

### Configuration

1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/yahapp.git
   ```

2. Add your API keys in local.properties:
   ```properties
   GEMINI_API_KEY=your_api_key_here
   ```

3. Sync project with Gradle files

4. Run the app on an emulator or device

</details>

<details>
<summary>📱 Features</summary>

### Core Functionality

1. **Authentication**
   - User Registration
   - Profile Management
   - Role-based Access (HR/Employee)

2. **Job Search**
   - AI-powered Job Recommendations
   - CV Upload & Analysis
   - Position Matching

3. **HR Tools**
   - Job Position Creation
   - Candidate Search
   - CV Review

4. **Chat Interface**
   - Natural Language Interaction
   - Job-related Queries
   - CV Analysis Results

### Screenshots

[Screenshots will be added here]

</details>

<details>
<summary>👥 Contributing</summary>

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

Please ensure your code follows our coding standards and includes appropriate tests.

</details>

## License 📄

```
MIT License

Copyright (c) 2025 YahApp

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```
