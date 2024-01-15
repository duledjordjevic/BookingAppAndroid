import java.util.Properties
plugins {
    id("com.android.application")
}
fun getIpAddress(): String {
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    return properties.getProperty("ip_addr") ?: error("IP address not found in local.properties")
}
android {
    namespace = "com.example.bookingapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bookingapplication"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "IP_ADDR", "\"${getIpAddress()}\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
//    compile("com.github.AnyChart:AnyChart-Android:0.0.3")
//    implementation("com.anychart:AnyChart-Android:1.1.2")
    implementation("com.github.PhilJay:MPAndroidChart:v3.0.3")

    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    implementation("androidx.navigation:navigation-ui:2.7.5")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.squareup.retrofit2:retrofit:2.3.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.nimbusds:nimbus-jose-jwt:9.8")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    implementation("io.coil-kt:coil-compose:2.1.0")
}