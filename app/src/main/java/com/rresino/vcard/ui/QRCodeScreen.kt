package com.rresino.vcard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rresino.vcard.ui.components.AddUrlDialog
import com.rresino.vcard.ui.components.EmptyState
import com.rresino.vcard.ui.components.QRCodeDisplay
import com.rresino.vcard.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRCodeScreen(
    viewModel: QRCodeViewModel = viewModel(factory = QRCodeViewModelFactory(LocalContext.current))
) {
    val urls by viewModel.urls.observeAsState(emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { maxOf(urls.size, 1) })

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Virtual cards App",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium
                    ) 
                },
                actions = {
                    if (urls.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                val currentUrl = urls.getOrNull(pagerState.currentPage)
                                currentUrl?.let { viewModel.deleteUrl(it) }
                            }
                        ) {
                            Icon(
                                Icons.Default.Delete, 
                                contentDescription = "Delete QR Code",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add URL")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            DeepBlue,
                            MidBlue,
                            LightBlue,
                            AccentBlue
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            if (urls.isEmpty()) {
                EmptyState(
                    onAddUrl = { showAddDialog = true }
                )
            } else {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    val url = urls.getOrNull(page)
                    if (url != null) {
                        QRCodeDisplay(url = url.url)
                    }
                }
                
                // Page indicator
                if (urls.size > 1) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(urls.size) { index ->
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(
                                        color = if (index == pagerState.currentPage) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                        },
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }

        AddUrlDialog(
            showDialog = showAddDialog,
            onDismiss = { showAddDialog = false },
            onAddUrl = { url ->
                viewModel.addUrl(url)
                showAddDialog = false
            }
        )
    }
}