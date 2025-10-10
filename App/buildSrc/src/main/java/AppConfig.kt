@file:Suppress("ConstPropertyName", "MemberVisibilityCanBePrivate")

import org.gradle.api.JavaVersion


//app level config constants
object AppConfig {
    const val applicationId = "com.deardhruv.adaptivelayout"
    const val name = "Adaptive Layout"
    const val versionCode = 1

    // @Suppress("ConstantLocale")
    val versionName: String
        get() = "1.0.0" // +
    // SimpleDateFormat("yyyy.MM.dd.HHmm", Locale.getDefault()).format(Date())

    // Android build stuff
    const val minSdk = 28
    const val targetSdk = 36
    const val compileSdk = 36
    const val buildToolsVersion = "36.1.0"

    // const val implementationSdkPreview = "UpsideDownCake"
    val javaVersion: JavaVersion = JavaVersion.VERSION_21

    val vyJvmTarget = "21" // org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_19
    val kotlinVersion = "2.2" // org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2

    const val ndkVersion = "29.0.14206865"

    const val dimension = "environment"

    const val devVariant = "dev"
    const val devAppLabel = "$name Dev"
    const val devApplicationIdSuffix = ""
    const val devVersionNameSuffix = "-dev"
    const val devAPI = ""

    const val prodVariant = "prod"
    const val prodAppLabel = name
    const val prodApplicationIdSuffix = ""
    const val prodVersionNameSuffix = ""
    const val prodAPI = ""


    // Release sign config
    const val storeFile = "../../Keystore/app_key.jks_key"
    const val storePassword = ""
    const val keyAlias = ""
    const val keyPassword = ""

    fun printAppInfo(
        applicationId: String?,
        buildToolsVersion: String?,
        versionName: String?,
        flavour: String,
        appName: String,
        kotlinApiVersion: String,
        languageVersion: String,
    ) {
        println("------------------------------------")

        print(LogColor.GREEN_BOLD_BRIGHT)
        println("appName: $appName")
        print(LogColor.GREEN_BOLD_BRIGHT)
        println("appPkg: " + (applicationId ?: ""))
        print(LogColor.GREEN_BOLD_BRIGHT)
        println("flavour: $flavour")

        print(LogColor.BLUE_BOLD_BRIGHT)
        println("versionName: " + (versionName ?: AppConfig.versionName))
        print(LogColor.BLUE_BOLD_BRIGHT)
        println("versionCode: $versionCode")

        buildToolsVersion?.let {
            print(LogColor.BLUE_BOLD_BRIGHT)
            println("buildToolsVersion: $it")
        }
        print(LogColor.BLUE_BOLD_BRIGHT)
        println("minSdk: $minSdk")
        print(LogColor.BLUE_BOLD_BRIGHT)
        println("targetSdk: $targetSdk")
        print(LogColor.BLUE_BOLD_BRIGHT)
        println("compileSdk: $compileSdk")

        print(LogColor.YELLOW_BOLD_BRIGHT)
        println("JavaVersion: $javaVersion")
        print(LogColor.YELLOW_BOLD_BRIGHT)
        println("JvmTarget: $vyJvmTarget")
        print(LogColor.YELLOW_BOLD_BRIGHT)
        println("kotlinApiVersion: $kotlinApiVersion")
        print(LogColor.YELLOW_BOLD_BRIGHT)
        println("languageVersion: $languageVersion")

        print(LogColor.RESET)
        println("------------------------------------")
    }
}

