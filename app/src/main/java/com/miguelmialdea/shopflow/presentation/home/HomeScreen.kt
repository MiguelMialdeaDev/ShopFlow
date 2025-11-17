package com.miguelmialdea.shopflow.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.miguelmialdea.shopflow.presentation.components.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProductClick: (Int) -> Unit,
    onCartClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val cartCount by viewModel.cartCount.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "ShopFlow",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(onClick = onCartClick) {
                        BadgedBox(
                            badge = {
                                if (cartCount > 0) {
                                    Badge(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    ) {
                                        Text(
                                            cartCount.toString(),
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Bar - Pill Style
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.searchProducts(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                placeholder = {
                    Text(
                        "Search products...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                singleLine = true,
                shape = MaterialTheme.shapes.extraLarge, // Pill shape
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            // Categories - Pill Chips
            if (uiState.categories.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        FilterChip(
                            selected = uiState.selectedCategory == "All",
                            onClick = { viewModel.filterByCategory("All") },
                            label = {
                                Text(
                                    "All",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (uiState.selectedCategory == "All")
                                        FontWeight.SemiBold else FontWeight.Normal
                                )
                            },
                            shape = MaterialTheme.shapes.extraLarge,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = if (uiState.selectedCategory != "All")
                                FilterChipDefaults.filterChipBorder(
                                    borderColor = MaterialTheme.colorScheme.outline,
                                    selectedBorderColor = MaterialTheme.colorScheme.primary
                                ) else null
                        )
                    }
                    items(uiState.categories) { category ->
                        FilterChip(
                            selected = uiState.selectedCategory == category,
                            onClick = { viewModel.filterByCategory(category) },
                            label = {
                                Text(
                                    category.replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (uiState.selectedCategory == category)
                                        FontWeight.SemiBold else FontWeight.Normal
                                )
                            },
                            shape = MaterialTheme.shapes.extraLarge,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = if (uiState.selectedCategory != category)
                                FilterChipDefaults.filterChipBorder(
                                    borderColor = MaterialTheme.colorScheme.outline,
                                    selectedBorderColor = MaterialTheme.colorScheme.primary
                                ) else null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Content
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = uiState.error ?: "An error occurred",
                                color = MaterialTheme.colorScheme.error
                            )
                            Button(onClick = { viewModel.loadProducts() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
                uiState.filteredProducts.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No products found")
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(
                            items = uiState.filteredProducts,
                            key = { it.id }
                        ) { product ->
                            ProductCard(
                                product = product,
                                onClick = { onProductClick(product.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
