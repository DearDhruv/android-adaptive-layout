import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrainsCompose)
    id(id = "kotlin-parcelize")
}


val vyJvmTarget = JvmTarget.fromTarget(AppConfig.vyJvmTarget)
val kotlinVersion = KotlinVersion.fromVersion(AppConfig.kotlinVersion)

val applicationLabelKey = "applicationLabel"
val applicationLabelSuffixKey = "applicationLabelSuffix"


android {

    namespace = AppConfig.applicationId
    compileSdk = AppConfig.compileSdk

    compileSdk {
        version = release(AppConfig.compileSdk)
    }

    defaultConfig {

        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        buildToolsVersion = AppConfig.buildToolsVersion
        manifestPlaceholders[applicationLabelKey] = AppConfig.name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = AppConfig.javaVersion
        targetCompatibility = AppConfig.javaVersion
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeCompiler {
        featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
    }
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }

    packaging.resources.excludes.addAll(
        listOf(
            "/*.properties",
            "META-INF/*.kotlin_module",
            "META-INF/*.properties",
            "META-INF/AL2.0",
            "META-INF/DEPENDENCIES",
            "META-INF/LGPL2.1",
            "META-INF/proguard/*",
            "/META-INF/{DEPENDENCIES}",
            "/META-INF/LICENSE.md",
            "META-INF/NOTICE.md",
        )
    )
}


androidComponents {
    onVariants(selector().withBuildType("release")) {
        it.packaging.resources.excludes.addAll(
            "/*.properties",
            "/META-INF/*",
            "META-INF/**",
            "META-INF/*.kotlin_module",
            "META-INF/*.properties",
            "META-INF/*.version",
            "META-INF/AL2.0",
            "META-INF/DEPENDENCIES",
            "META-INF/LGPL2.1",
            "META-INF/NOTICE.md",
            "META-INF/proguard/*",
        )
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(AppConfig.javaVersion.toString())
    }
}

kotlin {
    compilerOptions {
        jvmTarget = vyJvmTarget
        apiVersion.set(kotlinVersion)
        languageVersion.set(kotlinVersion)
        optIn.add("kotlin.RequiresOptIn")
    }
    target {
        compilerOptions {
            jvmTarget = vyJvmTarget
            apiVersion.set(kotlinVersion)
            languageVersion.set(kotlinVersion)
            optIn.add("kotlin.RequiresOptIn")
        }
    }
    jvmToolchain(AppConfig.javaVersion.toString().toInt())
}

dependencies {

    implementation(libs.androidx.activity)
    implementation(libs.androidx.annotation.jvm)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.kotlinx.coroutines.core)

    // Material
    implementation(libs.android.material)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material3)
    // implementation(libs.materialKolor)
    // runtimeOnly(libs.kotlinx.metadata.jvm)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.accompanist.permissions)


    // room
    // ksp(libs.androidx.room.compiler)
    // implementation(libs.androidx.room.runtime)
    // implementation(libs.androidx.room.ktx)
    // implementation(libs.androidx.privacysandbox.tools)

    // Firebase
    // implementation(platform(libs.firebase.bom))
    // implementation(libs.firebase.analytics)
    // implementation(libs.firebase.crashlytics.ktx)
    // implementation(libs.firebase.messaging.ktx)


    // GSON
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)

    // Network
    implementation(libs.httpclient5)
    implementation(libs.httpcore5)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)

    // navigation

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}