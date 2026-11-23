plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kover)
}

android {
    namespace = "duoc.cl"
    compileSdk = 37

    defaultConfig {
        applicationId = "duoc.cl"
        minSdk = 24
        targetSdk = 37
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

kover {
    reports {
        filters {
            excludes {
                classes(
                    "duoc.cl.MainActivity*",
                    "duoc.cl.camara.*",
                    "duoc.cl.ui.Navigation*",
                    "duoc.cl.ui.components.*",
                    "duoc.cl.ui.theme.*",
                    "duoc.cl.ui.role.*",
                    "duoc.cl.ui.auth.LoginScreen*",
                    "duoc.cl.ui.auth.RegisterScreen*",
                    "duoc.cl.ui.student.StudentScreen*",
                    "duoc.cl.ui.student.SubmitEntryScreen*",
                    "duoc.cl.ui.teacher.TeacherScreen*",
                    "duoc.cl.ui.teacher.CreateLogbookScreen*",
                    "duoc.cl.ui.teacher.LogbookDetailScreen*",
                    "duoc.cl.ui.apoderado.ApoderadoScreen*",
                    "duoc.cl.api.RetrofitClient*",
                    "duoc.cl.api.ApiService*"
                )
            }
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation("androidx.compose.material:material-icons-extended")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.play.services.location)

    // Retrofit & OkHttp
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)

    testImplementation(libs.junit)
    testImplementation("androidx.test:core-ktx:1.6.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")
    testImplementation("org.robolectric:robolectric:4.14.1")
    testImplementation("org.mockito:mockito-core:5.14.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
