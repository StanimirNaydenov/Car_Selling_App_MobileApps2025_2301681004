<img width="1024" height="1024" alt="logo" src="https://github.com/user-attachments/assets/b7083a11-852c-4c4a-84bc-02664863febc" />
CarMatrix
The CarMatrix is an advanced Android-based application used for buying and selling used cars. The app has an elegant interface and the trademarked 'Matrix Blue' colors, along with gesture control functionality, and an efficient local database management system.

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
Tech Stack
• Language: Kotlin
• Database: Room Persistence Library (SQLite)
• Architecture: MVVM (Model-View-ViewModel)
• Image Loading: Coil
• UI Components: Material Design 3, ConstraintLayout, CoordinatorLayout, ViewPager2
• Reactive UI: LiveData & Observer patterns

Screenshots
Splash Screen
<img width="564" height="1219" alt="Екранна снимка 2026-05-20 092429" src="https://github.com/user-attachments/assets/198078c3-d88f-431d-b67e-7f1699835750" />
Home & Filters
<img width="545" height="1224" alt="Екранна снимка 2026-05-23 101128" src="https://github.com/user-attachments/assets/8294a609-f5ef-4980-b6c7-51c4850dfce8" />
<img width="582" height="1230" alt="Екранна снимка 2026-05-23 101152" src="https://github.com/user-attachments/assets/94e150d2-bea9-4513-93f4-1e012ae2df5c" />
Car Details
<img width="565" height="1225" alt="Екранна снимка 2026-05-23 101223" src="https://github.com/user-attachments/assets/9a68206d-005f-40c5-826e-f5ce3c02fc63" />
Add/Edit Form
<img width="569" height="1224" alt="Екранна снимка 2026-05-23 101258" src="https://github.com/user-attachments/assets/606fffdd-af73-4acf-a612-10212252cff8" />
<img width="570" height="1224" alt="Екранна снимка 2026-05-23 101329" src="https://github.com/user-attachments/assets/aa69527d-6979-48a9-bfae-84bcb1eb64fc" />
Liked Page
<img width="581" height="1228" alt="Екранна снимка 2026-05-23 101418" src="https://github.com/user-attachments/assets/901f2212-8370-47d1-9e53-f92068fd8e86" />

Getting Started
Prerequisites
• Android Studio Jellyfish or newer.
• Minimum SDK: API 26 (Android 8.0 Oreo).
• Kotlin 1.8+

Database Schema
The app uses a single-table cars schema:
• id: Primary Key (Auto-increment)
• make/model/year/price: Core vehicle data.
• engineType/transmission/horsepower/mileage: Technical specs.
• imagePaths: Comma-separated internal storage paths.
• isLiked: Boolean flag for favorites.

Design System
• Primary Color: #00B8D9 (Matrix Blue)
• Backgrounds: #FFFFFF (White) / #EBEBEB (Light Gray)
• Typography: Bold headlines with Material 3 scaling.
