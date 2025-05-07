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
include(":app")

// 核心模塊
include(":core:ui")
include(":core:protocol")       // 協議實現 (原 mcp)
include(":core:inference")      // 推理引擎 (原 ai)
include(":core:toolkit")        // 工具框架 (原 tool)
include(":core:network")
include(":core:database")
include(":core:common")
include(":core:model-selector") // 模型選擇器組件

// 領域層模塊 - 按業務領域組織
include(":domain")

// 數據層模塊
include(":data")

// 功能模塊 - UI層按功能組織
include(":feature:conversation") // 對話功能 (原 chat)
include(":feature:chat-history") // 歷史記錄 (原 history)
include(":feature:settings")     // 設置功能
include(":feature:tool-hub")     // 工具管理 (原 tools)