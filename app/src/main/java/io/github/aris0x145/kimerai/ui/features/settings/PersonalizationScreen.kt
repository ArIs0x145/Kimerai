package io.github.aris0x145.kimerai.ui.features.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("個人化") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // 語言設定 - 使用下拉選單 (放大版)
            var expandedLanguageMenu by remember { mutableStateOf(false) }
            var selectedLanguage by remember { mutableStateOf("繁體中文") }
            val languageOptions = listOf("繁體中文", "English")
            
            ListItem(
                headlineContent = { Text("應用介面語言") },
                supportingContent = { Text("選擇應用程式的顯示語言") },
                leadingContent = { 
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingContent = {
                    Box {
                        Button(
                            onClick = { expandedLanguageMenu = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            modifier = Modifier.width(140.dp)
                        ) {
                            Text(selectedLanguage)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "選擇語言"
                            )
                        }
                        
                        DropdownMenu(
                            expanded = expandedLanguageMenu,
                            onDismissRequest = { expandedLanguageMenu = false },
                            modifier = Modifier.width(140.dp)
                        ) {
                            languageOptions.forEach { language ->
                                DropdownMenuItem(
                                    text = { 
                                        Text(
                                            text = language,
                                            modifier = Modifier.padding(4.dp)
                                        ) 
                                    },
                                    onClick = {
                                        selectedLanguage = language
                                        expandedLanguageMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            )
            
            HorizontalDivider()
            
            // 主題設定
            var isDarkMode by remember { mutableStateOf(false) }
            ListItem(
                headlineContent = { Text("明亮/暗黑模式") },
                supportingContent = { Text("切換應用程式的主題") },
                leadingContent = { 
                    Icon(
                        imageVector = Icons.Default.WbSunny,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingContent = {
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { isDarkMode = it }
                    )
                }
            )
            
            HorizontalDivider()
            
            // 字體大小調整 - 使用小型選項框在右邊
            var selectedFontSize by remember { mutableStateOf("中") }
            val fontSizeOptions = listOf("小", "中", "大")
            
            ListItem(
                headlineContent = { Text("字體大小") },
                supportingContent = { Text("調整應用程式字體大小") },
                leadingContent = { 
                    Icon(
                        imageVector = Icons.Default.TextFormat,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingContent = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        fontSizeOptions.forEach { option ->
                            val isSelected = option == selectedFontSize
                            Card(
                                onClick = { selectedFontSize = option },
                                modifier = Modifier.size(width = 36.dp, height = 28.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) 
                                        MaterialTheme.colorScheme.primaryContainer 
                                    else 
                                        MaterialTheme.colorScheme.surfaceVariant
                                ),
                                border = if (isSelected) 
                                    BorderStroke(1.dp, MaterialTheme.colorScheme.primary) 
                                else 
                                    null
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = option,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) 
                                            MaterialTheme.colorScheme.primary 
                                        else 
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            )
            
            HorizontalDivider()
            
            // 通知設定
            var notificationsEnabled by remember { mutableStateOf(true) }
            ListItem(
                headlineContent = { Text("通知") },
                supportingContent = { Text("管理應用程式通知") },
                leadingContent = { 
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingContent = {
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    )
                }
            )
        }
    }
}