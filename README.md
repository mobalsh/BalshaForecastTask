# BalshaForecastTask

## Overview
**BalshaForecastTask** is a mobile application that provides a daily weather forecast based on the user's selected city. The app retrieves weather data from the OpenWeatherMap API and caches it locally using Room for offline access. It features a simple and intuitive user interface that allows users to view the weather forecast for their chosen location easily.

## Features
- **City Selection**: Users can select a city from a dropdown menu.
- **Weather Forecast**: Displays the daily weather forecast, including temperature and weather conditions.
- **Local Caching**: Utilizes Room database for caching forecast data, providing access even when offline.
- **Error Handling**: Displays cached data with a warning if the data retrieval from the API fails.

## Tech Stack
- **Programming Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **Database**: Room
- **Networking**: Retrofit
- **Testing**: JUnit, Espresso

## Getting Started

### Prerequisites
- Android Studio installed on your machine
- JDK 11 or higher
- An API key from [OpenWeatherMap](https://openweathermap.org/api)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/mobalsh/BalshaForecastTask.git
   ```

2. Open the project in Android Studio.

3. Add your OpenWeatherMap API key in the appropriate place in the code.

4. You need to add this in the local.properties file

#

#KEY_STORE

RELEASE_STORE_PATH=PlayStore\\key.jks

RELEASE_STORE_PASSWORD=123456

RELEASE_KEY_ALIAS=key0

RELEASE_KEY_PASSWORD=123456

#

#DEBUG_KEYS

DEBUG_BASE_URL=https://api.openweathermap.org/data/2.5/

DEBUG_IMAGES_URL=https://openweathermap.org/img/wn/

#

#RELEASE_KEYS

RELEASE_BASE_URL=https://api.openweathermap.org/data/2.5/

RELEASE_IMAGES_URL=https://openweathermap.org/img/wn/

#

#APP_ID

APP_ID=7e1234faab3c6df6b46fe7c50fcef080

5. Sync the Gradle files to download the dependencies.

### Usage

1. Run the app on an emulator or a physical device.
2. Select a city from the dropdown menu.
3. View the weather forecast for the selected city.
4. If data retrieval fails, the app will display cached data with a warning message.

## Testing

Unit tests and UI tests are included in the project. To run the tests, use the following command in the terminal:

```bash
./gradlew test
./gradlew connectedAndroidTest
```

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments
- [OpenWeatherMap API](https://openweathermap.org/api) for providing weather data.
- [Room Documentation](https://developer.android.com/training/data-storage/room) for local data storage guidance.
- [Hilt Documentation](https://dagger.dev/hilt/) for dependency injection.

## Contact
For any questions or suggestions, please contact [GitHub Issues](https://github.com/mobalsh/BalshaForecastTask/issues).
