package io.github.aris0x145.kimerai.ui.navigation

/**
 * 定義應用的導航路由系統
 * 採用密封類別提供類型安全的路由定義，支援參數傳遞
 */
sealed class NavRoutes(val route: String) {
    /**
     * 主要導航目的地 (應用程式的主要區塊入口)
     */
    sealed class Main(route: String) : NavRoutes(route) {
        object Chat : Main("chat")
        object History : Main("history")
        object Settings : Main("settings")

        companion object {
            val routes = listOf(Chat, History, Settings)
        }
    }

    /**
     * 設定區塊相關路由
     * 基礎路徑: "settings"
     */
    sealed class Settings(routePath: String) : NavRoutes("settings" + if (routePath.isNotEmpty()) "/$routePath" else "") {
        // "settings/"
        object Main : Settings("")
        object Personalization : Settings("personalization")
        object Plugins : Settings("plugins")
        object Data : Settings("data")
        object About : Settings("about")

        // "settings/model/"
        sealed class Model(routePath: String) : Settings("model" + if (routePath.isNotEmpty()) "/$routePath" else "") {
            object Main : Model("")
            object ApiKeys : Model("api_keys")
            object Parameters : Model("parameters")

            companion object {
                val routes = listOf(ApiKeys, Parameters)
            }
        }

        companion object {
            val routes = listOf(Personalization, Model.Main, Plugins, Data, About)
        }
    }
}