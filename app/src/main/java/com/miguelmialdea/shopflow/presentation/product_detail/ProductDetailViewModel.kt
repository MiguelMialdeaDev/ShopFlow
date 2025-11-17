package com.miguelmialdea.shopflow.presentation.product_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelmialdea.shopflow.domain.model.CartItem
import com.miguelmialdea.shopflow.domain.model.Product
import com.miguelmialdea.shopflow.domain.repository.CartRepository
import com.miguelmialdea.shopflow.domain.repository.ProductRepository
import com.miguelmialdea.shopflow.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: Int = savedStateHandle.get<Int>("productId") ?: 0

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    val cartCount: StateFlow<Int> = cartRepository.getCartCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            productRepository.getProductById(productId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                product = result.data,
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

    fun incrementQuantity() {
        _uiState.update { it.copy(quantity = it.quantity + 1) }
    }

    fun decrementQuantity() {
        if (_uiState.value.quantity > 1) {
            _uiState.update { it.copy(quantity = it.quantity - 1) }
        }
    }

    fun addToCart() {
        val product = _uiState.value.product ?: return
        val quantity = _uiState.value.quantity

        viewModelScope.launch {
            val cartItem = CartItem(
                productId = product.id,
                title = product.title,
                price = product.price,
                image = product.image,
                quantity = quantity
            )
            cartRepository.addToCart(cartItem)
            _uiState.update { it.copy(showAddedToCartMessage = true) }
        }
    }

    fun clearAddedToCartMessage() {
        _uiState.update { it.copy(showAddedToCartMessage = false) }
    }
}

data class ProductDetailUiState(
    val product: Product? = null,
    val quantity: Int = 1,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showAddedToCartMessage: Boolean = false
)
