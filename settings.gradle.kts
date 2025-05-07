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
    }
}

rootProject.name = "Kimerai"

// Application
include(":app")

// Core
include(":core:ui")
include(":core:protocol")   
include(":core:inference")
include(":core:agent-tools")
include(":core:network")
include(":core:database")
include(":core:common")
include(":core:model-selector")
include(":core:navigation")

// Domain
include(":domain")

// Data
include(":data")

// Feature
include(":feature:conversation")
include(":feature:chat-history")
include(":feature:settings")
include(":feature:tool-hub")