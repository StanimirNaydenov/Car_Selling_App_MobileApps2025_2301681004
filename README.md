<img width="600" height="600" alt="logo" src="https://github.com/user-attachments/assets/b7083a11-852c-4c4a-84bc-02664863febc" />

----------------------------------------------------------------------------------------------------------------------------------------

<h1>CarMatrix</h1>

The CarMatrix is an advanced Android-based application used for buying and selling used cars. The app has an elegant interface and the trademarked 'Matrix Blue' colors, along with gesture control functionality, and an efficient local database management system.

----------------------------------------------------------------------------------------------------------------------------------------

<h1>Features</h1>

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

<h1>Tech Stack</h1>

• Language: Kotlin

• Database: Room Persistence Library (SQLite)

• Architecture: MVVM (Model-View-ViewModel)

• Image Loading: Coil

• UI Components: Material Design 3, ConstraintLayout, CoordinatorLayout, ViewPager2

• Reactive UI: LiveData & Observer patterns

----------------------------------------------------------------------------------------------------------------------------------------

<h1>Getting Started</h1>

<h3>Prerequisites</h3>

• Android Studio Jellyfish or newer.

• Minimum SDK: API 26 (Android 8.0 Oreo).

• Kotlin 1.8+

----------------------------------------------------------------------------------------------------------------------------------------

<h1>Application architecture</h1>

The application follows the MVVM (Model-View-ViewModel) architectural pattern for clean logic and easy maintenance:

<h2>Data Layer (Model)</h2>

•Entity – Car.kt: Defines the structure of the car (make, model, year, price, power, mileage, engine type, gearbox, photos and "liked" status).

•DAO – CarDao.kt: Contains the SQL queries for working with the database, including methods for filtering by make (getCarsByMake) and managing favorite cars.

•Database – CarDatabase.kt: Singleton instance of Room DB (Version 3), ensuring data persistence.

<h2>ViewModel Layer</h2>

•CarViewModel.kt: Mediator between the database and the interface.

◦Uses viewModelScope for asynchronous operations (write/delete) to avoid "freezing" the application.

◦Provides LiveData objects (allCars, likedCars), which automatically refresh the screen when the data changes.

<h2>UI layer (View)</h2>

The application is organized through Activities that inherit BaseActivity for shared header and navigation:

•SplashActivity: Home screen with branded logo (4 seconds).

•MainActivity: Central screen with a list of cars, dynamic filtering by brand (Mercedes, BMW, etc.) and a swipe to like gesture.

•CarDetailActivity: Detailed view with interactive gallery (ViewPager2), technical data and quick action buttons (Call/Share).

•AddCarActivity: Universal form for adding new and editing existing ads with attaching up to 5 photos.

•LikedActivity: List of only the cars liked by the user.

<h1>Database Schema</h1>

The app uses a single-table cars schema:

• id: Primary Key (Auto-increment)

• make/model/year/price: Core vehicle data.

• engineType/transmission/horsepower/mileage: Technical specs.

• imagePaths: Comma-separated internal storage paths.

• isLiked: Boolean flag for favorites.

<h2>Data table (Cars)</h2>

| Column | Type | Description |
|---------|------|-----------|
| id | INTEGER | Automatic primary key |
| make | TEXT | Brand (Mercedes, BMW, Audi, etc.) |
| model | TEXT | Car model |
| price | REAL | Car price in Euro (€) |
| imagePaths | TEXT | List of paths to photos (comma separated) |
| isLiked | INTEGER | Favorites status (0 or 1) |

----------------------------------------------------------------------------------------------------------------------------------------

<h1>Design System</h1>

• Primary Color: #00B8D9 (Matrix Blue)

• Backgrounds: #FFFFFF (White) / #EBEBEB (Light Gray)

• Typography: Bold headlines with Material 3 scaling.

----------------------------------------------------------------------------------------------------------------------------------------

<h1> For download</h1>
<h2> Only for Android devices</h2>
[Download APK] (https://github.com/StanimirNaydenov/Car_Selling_App_MobileApps2025_2301681004/releases/tag/v1.0)

<h1>Screenshots</h1>

<h2>Splash Screen</h2>

<img width="567" height="1229" alt="Екранна снимка 2026-05-23 102859" src="https://github.com/user-attachments/assets/430ca1b1-485b-4079-b768-e4be1dadb379" />

----------------------------------------------------------------------------------------------------------------------------------------

<h2>Home & Filters</h2>

<img width="545" height="1224" alt="Екранна снимка 2026-05-23 101128" src="https://github.com/user-attachments/assets/e2fdea81-34ed-4b0d-8ea5-14d3b7b7b562" />

<img width="582" height="1230" alt="Екранна снимка 2026-05-23 101152" src="https://github.com/user-attachments/assets/f1f2b352-7b85-4e17-814b-ca8fbfefc761" />

----------------------------------------------------------------------------------------------------------------------------------------

<h2>Car Details</h2>

<img width="565" height="1225" alt="Екранна снимка 2026-05-23 101223" src="https://github.com/user-attachments/assets/937ec188-0e69-4693-ada1-7a8324c21460" />

----------------------------------------------------------------------------------------------------------------------------------------

<h2>Add/Edit Form</h2>

<img width="569" height="1224" alt="Екранна снимка 2026-05-23 101258" src="https://github.com/user-attachments/assets/6beddf12-5737-4b4e-a87c-e39c0552a6e5" />

<img width="570" height="1224" alt="Екранна снимка 2026-05-23 101329" src="https://github.com/user-attachments/assets/224f0d27-3d4c-4a55-ae41-a8afe7bc3f96" />

----------------------------------------------------------------------------------------------------------------------------------------

<h2>Liked Page</h2>

<img width="581" height="1228" alt="Екранна снимка 2026-05-23 101418" src="https://github.com/user-attachments/assets/159a71cd-2394-4b60-83e8-6608ff4bc6e3" />








