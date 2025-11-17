package com.miguelmialdea.shopflow.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelmialdea.shopflow.domain.model.Product
import com.miguelmialdea.shopflow.domain.repository.CartRepository
import com.miguelmialdea.shopflow.domain.repository.ProductRepository
import com.miguelmialdea.shopflow.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val cartCount: StateFlow<Int> = cartRepository.getCartCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    init {
        loadProducts()
        loadCategories()
    }

    fun loadProducts() {
        viewModelScope.launch {
            productRepository.getProducts().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                products = result.data ?: emptyList(),
                                filteredProducts = result.data ?: emptyList(),
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            productRepository.getCategories().collect { result ->
                if (result is Resource.Success) {
                    _uiState.update {
                        it.copy(categories = result.data ?: emptyList())
                    }
                }
            }
        }
    }

    fun filterByCategory(category: String) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }

        if (category == "All") {
            _uiState.update {
                it.copy(filteredProducts = it.products)
            }
        } else {
            viewModelScope.launch {
                productRepository.getProductsByCategory(category).collect { result ->
                    if (result is Resource.Success) {
                        _uiState.update {
                            it.copy(filteredProducts = result.data ?: emptyList())
                        }
                    }
                }
            }
        }
    }

    fun searchProducts(query: String) {
        _uiState.update { it.copy(searchQuery = query) }

        val filtered = if (query.isBlank()) {
            _uiState.value.products
        } else {
            _uiState.value.products.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }

        _uiState.update { it.copy(filteredProducts = filtered) }
    }
}

data class HomeUiState(
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "All",
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
