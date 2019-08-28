
const val kotlin_version = "1.3.50"

object Sdk {
    val minSdk = 15
    val targetSdk = 28
}

object Android {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    val appCompat = "androidx.appcompat:appcompat:1.0.2"
    val ktx = "androidx.core:core-ktx:1.0.2"
    val constraint = "androidx.constraintlayout:constraintlayout:1.1.3"
}

object Testing {
    val junit = "junit:junit:4.12"
}

object AndroidTesting {
    val runner = "androidx.test:runner:1.2.0"
    val espresso = "androidx.test.espresso:espresso-core:3.2.0"
}

object Design {
    val material = "com.google.android.material:material:1.1.0-alpha09"
    val recyclingPaged = "com.utsman.recycling-paged:recycling:0.1.2"
}

object Glide {
    val glide = "com.github.bumptech.glide:glide:4.9.0"
    val compiler = "com.github.bumptech.glide:compiler:4.9.0"
    val okhttp = "com.squareup.okhttp3:okhttp:4.0.1"
}

object Rx {
    val rxJava = "io.reactivex.rxjava2:rxjava:2.2.9"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    val rxNetwork = "com.github.pwittchen:reactivenetwork-rx2:3.0.2"
}

object Lifecycle {
    val lifecycle = "androidx.lifecycle:lifecycle-extensions:2.0.0"
}

object Room {
    val room = "androidx.room:room-runtime:2.1.0"
    val rxSupport = "androidx.room:room-rxjava2:2.1.0"
    val compiler = "androidx.room:room-compiler:2.1.0"
}

object Paging {
    val paging = "androidx.paging:paging-runtime-ktx:2.1.0"
    val rxSupport = "androidx.paging:paging-rxjava2:2.1.0"
}

object Retrofit {
    val retrofit = "com.squareup.retrofit2:retrofit:2.5.0"
    val gsonConverter = "com.squareup.retrofit2:converter-gson:2.5.0"
    val rxAdapter = "com.squareup.retrofit2:adapter-rxjava2:2.5.0"
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.0.1"
}

object Module {
    val core = ":core"
    val unsplash = ":unsplash"
    val pexel = ":pexel"
}