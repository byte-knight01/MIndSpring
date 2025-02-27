pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io")
            maven { url = uri("https://jcenter.bintray.com") }  // Add this
//            maven { url = uri("https://jitpack.io") }  // Make sure this exists
    }
}

rootProject.name = "MindSpring"
include(":app")}
 