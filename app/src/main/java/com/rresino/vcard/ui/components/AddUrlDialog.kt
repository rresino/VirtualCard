package com.rresino.vcard.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddUrlDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAddUrl: (String) -> Unit
) {
    if (showDialog) {
        var urlText by remember { mutableStateOf("") }
        var isError by remember { mutableStateOf(false) }
        
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Add New URL",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    
                    OutlinedTextField(
                        value = urlText,
                        onValueChange = { 
                            urlText = it
                            isError = false
                        },
                        label = { Text("Enter URL") },
                        placeholder = { Text("https://example.com") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = isError,
                        supportingText = if (isError) {
                            { Text("Please enter a valid URL") }
                        } else null
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("Cancel")
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Button(
                            onClick = {
                                if (isValidUrl(urlText)) {
                                    onAddUrl(urlText.trim())
                                    urlText = ""
                                    onDismiss()
                                } else {
                                    isError = true
                                }
                            }
                        ) {
                            Text("Add")
                        }
                    }
                }
            }
        }
    }
}

private fun isValidUrl(url: String): Boolean {
    return url.isNotBlank() && (
        url.startsWith("http://", ignoreCase = true) || 
        url.startsWith("https://", ignoreCase = true) ||
        url.contains(".", ignoreCase = true)
    )
}