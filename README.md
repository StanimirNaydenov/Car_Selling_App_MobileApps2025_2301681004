<img width="600" height="600" alt="logo" src="https://github.com/user-attachments/assets/b7083a11-852c-4c4a-84bc-02664863febc" />

----------------------------------------------------------------------------------------------------------------------------------------

CarMatrix

The CarMatrix is an advanced Android-based application used for buying and selling used cars. The app has an elegant interface and the trademarked 'Matrix Blue' colors, along with gesture control functionality, and an efficient local database management system.

----------------------------------------------------------------------------------------------------------------------------------------
Features

• Dynamic Home Screen: Personal greetings, interactive service banners, and a categorized view of the latest car listings.

• Smart Brand Filtering: Quick filters for popular brands like Mercedes, BMW, Audi, and Toyota. The UI updates dynamically to show counts or "Not Found" states.

• Gesture-Based Interaction:

   ◦ Swipe Right: Toggle "Favorite" status instantly from any list.
   
   ◦ Long Press: Access administrative options (Edit, Share, Delete).
   
• Detailed Vehicle Insights:

   ◦ Interactive Image Carousel (ViewPager2) for car photos.
   
   ◦ One-click Direct Dial to contact sellers.
   
   ◦ Comprehensive technical specifications grid.
   
• Favorites Management: A dedicated space to track liked cars with a bulk "Clear All" functionality.

• Robust CRUD Operations: Full capability to Add, Edit, and Delete car ads, including local storage of up to 5 images per ad.

----------------------------------------------------------------------------------------------------------------------------------------

Tech Stack

• Language: Kotlin

• Database: Room Persistence Library (SQLite)

• Architecture: MVVM (Model-View-ViewModel)

• Image Loading: Coil

• UI Components: Material Design 3, ConstraintLayout, CoordinatorLayout, ViewPager2

• Reactive UI: LiveData & Observer patterns

----------------------------------------------------------------------------------------------------------------------------------------

Getting Started
Prerequisites

• Android Studio Jellyfish or newer.

• Minimum SDK: API 26 (Android 8.0 Oreo).

• Kotlin 1.8+

----------------------------------------------------------------------------------------------------------------------------------------

Database Schema

The app uses a single-table cars schema:

• id: Primary Key (Auto-increment)

• make/model/year/price: Core vehicle data.

• engineType/transmission/horsepower/mileage: Technical specs.

• imagePaths: Comma-separated internal storage paths.

• isLiked: Boolean flag for favorites.

----------------------------------------------------------------------------------------------------------------------------------------

Design System

• Primary Color: #00B8D9 (Matrix Blue)

• Backgrounds: #FFFFFF (White) / #EBEBEB (Light Gray)

• Typography: Bold headlines with Material 3 scaling.

