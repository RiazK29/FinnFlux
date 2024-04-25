# Project Name: FinnFlux

## Class Diagram and Descriptions

### Main Activities and Fragments
- `MainActivity`: Manages the main screen of the application and the navigation menu below it.
- `FragmentQuiz`: Presents true/false questions to the user.
- `FragmentTilastokeskus`: Displays population statistics and retrieves data from an API.
- `FragmentWeather`: Displays weather information and retrieves weather data from an API.
- `FragmentWikipedia`: Retrieves and displays information from the Wikipedia API.

### Data Retrievers
- `WeatherRetriever`: Retrieves weather data from the OpenWeatherMap API.
- `WikipediaRetriever`: Retrieves summary information from Wikipedia's REST API.
- `CitizenshipRetriever`: Retrieves and processes population data from an API.

### Interfaces
- `WeatherInterfaces`: Used to transmit weather data to the fragment.
- `WikipediaInterfaces`: Used to transmit data retrieved from Wikipedia to the fragment.
- `CitizenshipInterface`: Used to transmit population statistics to the fragment.

### Models
- `WeatherModel`: Stores weather data.
- `WikipediaModel`: Stores information retrieved from Wikipedia.
- `CitizenshipData`: Stores population statistics data.
- `TrueOrFalseModel`: Stores true/false questions.

### Utilities
- `JsonUtils`: Reads and processes JSON files.

## Installation and Configuration
1. Open Android Studio.
2. Download the project.
3. Open the project in Android Studio using the `Open an Existing Project` option.
4. Install all necessary dependencies and build the project.
5. Run the project on an emulator or a real device.

## Features
- Display a variety of information: weather, Wikipedia summaries, population statistics, and quizzes.
- Dynamic data infrastructure with API integration.
- User-friendly interface and navigation.

## Developer Notes
- Update your API keys in the `Configs` class.
