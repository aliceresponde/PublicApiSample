# PublicApiSample

Instructions : User your own AP_KEY from https://www.yelp.com/developers/v3/manage_app

Use you our API KEY, and replace it on your build.gradle(:app) line 27
            buildConfigField "String", "API_KEY_VALUE", '"API_KEY_VALUE"'
             Replace tAPI_KEY_VALUE by something like:
             Bearer APLI_KEY
             
#Features
Get business places in different contries, just select the place, then you can see a list of those, or the related detail.
The app require internet connection but if you lost connection the chache strategy let you check persisted info.

# Architecture
![alt text](https://github.com/aliceresponde/CountingApp/blob/master/info/android_clean_repository_arch.png)

# Used Libraries
* [Android X Preference](https://developer.android.com/jetpack/androidx/releases/preference)
* [Data Binding](https://codelabs.developers.google.com/codelabs/android-databinding/index.html?index=..%2F..index#5)
* [Navigation Component](https://codelabs.developers.google.com/codelabs/android-navigation/index.html?index=..%2F..index#0)
* [Room](https://developer.android.com/jetpack/androidx/releases/room)
* [Retrofit2](https://square.github.io/retrofit/)
* [Coroutines](https://developer.android.com/kotlin/coroutines)
* [Glide] for image management

# Best Practices And References
[Dependency Inversion Principle (DIP)](https://martinfowler.com/articles/dipInTheWild.html)(without frameworks)
[Clean Code](https://www.amazon.com/-/es/Robert-C-Martin/dp/0132350882)

             
             
