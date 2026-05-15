# Grama-Suvidha Portal

**Grama-Suvidha** is a transparency-focused Android project tracker built for the MindMatrix VTU Internship Program. It empowers citizens to stay informed about their community's development by listing government-funded projects, visualizing their "Digital Progress", and enabling feedback submission.

## Problem Statement
In many rural areas, there is a significant lack of transparency and accessibility regarding government-funded infrastructure projects. Citizens often struggle to stay informed about the progress of local development, understand how public funds are allocated, or report issues directly to the authorities. Existing platforms are rarely optimized for rural demographics, often lacking native language support (like Kannada) and offline accessibility. This disconnect breeds mistrust and prevents the community from actively participating in their village's growth.

## Features & Implementation
- **Project List & Progress View:** Uses modern Jetpack Compose UI to dynamically display project data, including a sophisticated interactive "Before & After" image slider.
- **Citizen Feedback:** A fully interactive feedback block allowing users to rate the infrastructure (1-5 stars) and submit text/image issue reports.
- **Bilingual Support (Kannada & English):** Native toggle to translate the entire UI to Kannada, ensuring every villager can participate.
- **Offline Caching:** Project data is cached locally. The architecture parses the local JSON "Mock API" and provides instant offline viewing after the initial mock fetch.

## Technical Architecture
The application is built using the **MVVM (Model-View-ViewModel)** architectural pattern:
- **UI Layer (Jetpack Compose):** A state-driven UI composed of premium, glassmorphic Material 3 components.
- **ViewModel (`ProjectViewModel`):** Leverages `StateFlow` to manage UI state, sorting, filtering, and simulating network delays.
- **Data Layer (`ProjectRepository`):** Responsible for parsing the Mock API and caching the data in memory for instant offline retrieval.
- **Images (Coil):** Uses the Coil library to asynchronously load and cache project images.

## Technologies Used
* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (with Material 3)
* **Architecture:** MVVM (Model-View-ViewModel)
* **Asynchronous Programming:** Kotlin Coroutines & StateFlow
* **Image Loading:** Coil
* **Data Parsing:** Kotlinx Serialization
* **Build System:** Gradle (Kotlin DSL)

## Mock API Documentation

To simulate backend communication and demonstrate offline caching, the application uses a local JSON file (`assets/projects.json`) acting as a Mock API response. 

### JSON Structure

The JSON structure is an array of `Project` objects. Each object contains the necessary bilingual data for rendering the UI.

```json
[
  {
    "id": 1,
    "titleEn": "Main Road Repair",
    "titleKn": "ಮುಖ್ಯ ರಸ್ತೆ ದುರಸ್ತಿ",
    "budget": "₹5,00,000",
    "completionDate": "2024-05-15",
    "progress": 75,
    "statusEn": "In Progress",
    "statusKn": "ಪ್ರಗತಿಯಲ್ಲಿದೆ",
    "beforeImageUrl": "file:///android_asset/road_before.jpg",
    "afterImageUrl": "file:///android_asset/road_after.jpg",
    "descriptionEn": "Repairing the main approach road to the village center, including filling potholes and resurfacing.",
    "descriptionKn": "ಗ್ರಾಮದ ಕೇಂದ್ರಕ್ಕೆ ಹೋಗುವ ಮುಖ್ಯ ರಸ್ತೆಯ ದುರಸ್ತಿ."
  }
]
```

### Fields:
*   `id` (Integer): Unique identifier for the project.
*   `titleEn` / `titleKn` (String): The project name in English and Kannada.
*   `budget` (String): The allocated budget (e.g., "₹5,00,000").
*   `completionDate` (String): Expected completion date (YYYY-MM-DD).
*   `progress` (Integer): The completion percentage (0 - 100). The UI progress bar scales dynamically based on this integer.
*   `statusEn` / `statusKn` (String): Current status text ("In Progress", "Completed", "Not Started").
*   `beforeImageUrl` / `afterImageUrl` (String): URLs or local asset paths pointing to the project photos. Coil loads these asynchronously.
*   `descriptionEn` / `descriptionKn` (String): A detailed description of the infrastructure work.

## Success Criteria Checklist
- [x] Offline viewing supported (via local JSON parsing / caching).
- [x] Progress bar accurately reflects DB percentage (mapped to `StateFlow` UI states).
- [x] Full Kannada/English toggle.
- [x] Mock API Structure documented (See above).
