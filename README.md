# ShopFlow - Modern E-commerce Android App

A complete, production-ready e-commerce application built with the latest Android technologies and best practices.

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![MinSDK](https://img.shields.io/badge/MinSDK-24-orange.svg)
![License](https://img.shields.io/badge/License-MIT-red.svg)

## Features

 **Product Catalog** - Browse products with search and category filters
 **Product Details** - Detailed view with images, ratings, and descriptions
 **Shopping Cart** - Add, update, and remove items with real-time calculations
 **Checkout Flow** - Complete checkout process with form validation
 **Offline Support** - Local caching with Room Database
 **Clean Architecture** - Separation of concerns with presentation, domain, and data layers
 **Material Design 3** - Modern, beautiful UI following Material guidelines
 **Responsive Design** - Optimized for different screen sizes

## Tech Stack

### Architecture & Patterns
- **MVVM (Model-View-ViewModel)** - Presentation layer architecture
- **Clean Architecture** - Separation into presentation, domain, and data layers
- **Repository Pattern** - Abstract data sources
- **Use Cases** - Business logic encapsulation
- **Dependency Injection** - Hilt for DI

### Libraries & Technologies

| Category | Technology |
|----------|-----------|
| **Language** | Kotlin 100% |
| **UI Framework** | Jetpack Compose |
| **Architecture Components** | ViewModel, LiveData, Navigation |
| **Async Programming** | Coroutines, Flow |
| **Networking** | Retrofit, OkHttp, Gson |
| **Local Database** | Room |
| **Dependency Injection** | Hilt/Dagger |
| **Image Loading** | Coil |
| **Material Design** | Material 3 Components |

## Project Structure

```
app/
├── data/                           # Data layer
│   ├── local/                      # Local data sources
│   │   ├── dao/                    # Room DAOs
│   │   ├── entity/                 # Room entities
│   │   └── database/               # Database setup
│   ├── remote/                     # Remote data sources
│   │   ├── api/                    # Retrofit API services
│   │   └── dto/                    # Data transfer objects
│   └── repository/                 # Repository implementations
│
├── domain/                         # Domain layer (business logic)
│   ├── model/                      # Domain models
│   └── repository/                 # Repository interfaces
│
├── presentation/                   # Presentation layer (UI)
│   ├── home/                       # Home screen
│   ├── product_detail/             # Product detail screen
│   ├── cart/                       # Shopping cart screen
│   ├── checkout/                   # Checkout screen
│   ├── components/                 # Reusable UI components
│   └── navigation/                 # Navigation setup
│
├── di/                             # Dependency injection modules
└── util/                           # Utility classes
```

## Architecture

The app follows **Clean Architecture** principles with three main layers:

### Presentation Layer
- **Composables**: UI components built with Jetpack Compose
- **ViewModels**: Manage UI state and business logic
- **Navigation**: Handle navigation between screens

### Domain Layer
- **Models**: Business entities
- **Repository Interfaces**: Define data operations
- **Use Cases**: Encapsulate business logic (optional)

### Data Layer
- **Repositories**: Implement domain repository interfaces
- **Data Sources**:
  - **Remote**: Retrofit API calls to Fake Store API
  - **Local**: Room database for offline caching
- **DTOs & Entities**: Data models for network and database

## API

This app uses the [Fake Store API](https://fakestoreapi.com/), a free REST API for testing and prototyping.

**Base URL:** `https://fakestoreapi.com/`

**Main Endpoints:**
- `GET /products` - Get all products
- `GET /products/{id}` - Get product by ID
- `GET /products/categories` - Get all categories
- `GET /products/category/{category}` - Get products by category

## Getting Started

### Prerequisites
- Android Studio Hedgehog | 2023.1.1 or higher
- JDK 11 or higher
- Android SDK API 24 or higher

### Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/shopflow.git
cd shopflow
```

2. Open the project in Android Studio

3. Sync Gradle files

4. Run the app on an emulator or physical device

### Build & Run

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test
```

## Key Features Implementation

### 1. Product Catalog
- Grid layout with 2 columns
- Search functionality
- Category filtering
- Pull-to-refresh
- Loading states and error handling

### 2. Product Details
- High-quality product images
- Rating display
- Quantity selector
- Add to cart functionality
- Detailed description

### 3. Shopping Cart
- View all cart items
- Update quantities
- Remove items
- Real-time total calculation
- Empty state handling

### 4. Checkout
- Form validation
- Shipping information input
- Payment details (mock)
- Order summary
- Processing state

## Best Practices

 **Single Responsibility Principle**: Each class has one responsibility
 **Dependency Inversion**: Depend on abstractions, not implementations
 **Separation of Concerns**: Clear layer separation
 **Immutability**: Data classes and StateFlow for reactive state
 **Error Handling**: Proper error states and user feedback
 **Loading States**: Skeleton screens and progress indicators
 **Empty States**: Meaningful empty state messages
 **Type Safety**: Sealed classes for navigation and resource states

## Performance Optimizations

- **LazyColumn/LazyGrid** for efficient list rendering
- **Image caching** with Coil
- **Database queries** optimized with indices
- **Coroutines** for async operations
- **StateFlow** for reactive state management
- **Hilt** for efficient dependency injection

## Testing

The project includes:
- **Unit Tests**: ViewModel and Repository tests
- **Integration Tests**: Database tests
- **UI Tests**: Compose UI tests

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## Screenshots

*Add screenshots here after running the app*

| Home Screen | Product Detail | Cart | Checkout |
|------------|----------------|------|----------|
| ![Home](screenshots/home.png) | ![Detail](screenshots/detail.png) | ![Cart](screenshots/cart.png) | ![Checkout](screenshots/checkout.png) |

## Future Enhancements

- [ ] User authentication
- [ ] Wishlist/Favorites functionality
- [ ] Order history
- [ ] Push notifications
- [ ] Product reviews and ratings
- [ ] Multiple payment methods
- [ ] Order tracking
- [ ] Dark mode support
- [ ] Multi-language support
- [ ] Analytics integration

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

**Miguel Ángel Mialdea**
- Android Developer | 5+ years experience
- Specialized in Kotlin, Jetpack Compose, Clean Architecture
- Banking & FinTech expertise (Openbank/Santander Group)

## Contributing

Contributions, issues, and feature requests are welcome!

## Acknowledgments

- [Fake Store API](https://fakestoreapi.com/) for providing free API
- [Material Design](https://m3.material.io/) for design guidelines
- Android community for excellent libraries and tools

---

**Built with ❤️ using Kotlin and Jetpack Compose**
