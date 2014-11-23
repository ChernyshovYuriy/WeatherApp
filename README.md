WeatherApp
==========

This is a simple weather application.
It uses OpenWeatherMap (http://openweathermap.org/) resource in order to get information about weather.
API can be found at http://openweathermap.org/api
For a simplicity application use Current weather data API http://openweathermap.org/current
Application is developing under Android Studio and driven by the Gradle

Requirements:
- Android API levels Min: "14", Compile and Target: "21"
- Android Build Tools Version: "21.1.1"
- Gradle Version 2.1 "https://services.gradle.org/distributions/gradle-2.1-all.zip"
- Android Studio Version Beta 0.8.14

Main concepts which are used by this Application:
- In order to download data from the weather service Android wramework capabilities are used,
  such that IntentService which is able to manage its own lifecycle and destroy itself uppon task is completed.
- IntentService call it's onHandleIntent hoock method in non UI thread which allows to perform long running
  operations whithout creating additional Thread.
- Messenger framework togather with Handler object allows to exchange information between UI components
  and a Service itself which complete decouples UI components from the business logic. UI component know nothing
  about data management (at least I tried to achieve this goal), Service know nothing about UI components.
  These things are completly decoupled from each other.
- OnSaveInstanceState is using in order to save Activities state before it's goign to be destroyed in case it will
  be re-created in very nearest future, for example when device is rotating. This prevent application from
  unnecessary downloding process.
- Interfaces are widely used in order to provide flexibility and extensibility of the Application. For example,
  Downloader interface allows to implement different variants of the downloading process:
  download over HTTP, TCP, or any other protocol or way. DataParser allows to implement different types of data
  parsing, such as JSON, XML or any other format. In present implementation it use JSON one.
- At last but nevertheless important is a concept to avoid NULL as a way to perform default behaviour.
  What I tried to show in this application is that any object must have it's own default state, default value,
  and it must not be NULL. From my point of view - NULL is a bed practice and I try to avoid it.

In order to provide robustness and perform Test Drive Development while develop application, and also
to be sure that new functionality does not brake existed one - Android Test framework is used.
There are only necessary test exists in the project up to now, just to test bound conditions, null references,
creation of the objects and their main functionalities. But the number of the Unit and Integrational test can be
increased in nearest future.

What this applicaition can do so far:
- According to API http://openweathermap.org/current download weather data, serialize it into Java object.
- Display current temperature, humidity and condition icon
- Select one of the thre city which are provided with drop down list
- Select temperature format to display (Celsius or Fahrenheit)
- Keep weather data in memory and restore it from memory uppon device rotation

What features can be added according to existing implementation:
- More main information can be displayed
- Refresh button can be added (or set refresh interval in the setting dialog)
- Setting itself can be set in the Android SettingActivity framework ruther then use SharedPreferences
- Dialog Frame can be used in order to display all the rest information regarding weather condition which
  are received from the initial download call (lets say "Extra Information")
- Extend API usage (as this concrete web resource provides a huge ability)
