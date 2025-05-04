package io.github.aris0x145.kimerai.ui.features.settings.models.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 模型列表項組件
 * 用於在供應商設定頁面顯示可用模型
 */
@Composable
fun ModelListItem(
    modifier: Modifier = Modifier,
    modelName: String,
    modelDescription: String,
    isEnabled: Boolean,
    onEnableChange: (Boolean) -> Unit,
    onSettingsClick: () -> Unit,
    capabilities: List<String> = emptyList()
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = modelName,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = modelDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                // 能力標籤
                if (capabilities.isNotEmpty()) {
                    Row(
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        capabilities.take(3).forEach { capability ->
                            SuggestionChip(
                                onClick = { /* 不做任何事 */ },
                                label = { 
                                    Text(
                                        text = capability,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        }
                    }
                }
            }
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onSettingsClick) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "模型設定"
                    )
                }
                
                Switch(
                    checked = isEnabled,
                    onCheckedChange = onEnableChange
                )
            }
        }
    }
}