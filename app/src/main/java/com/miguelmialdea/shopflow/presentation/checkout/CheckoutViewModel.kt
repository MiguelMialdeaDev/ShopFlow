package com.miguelmialdea.shopflow.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelmialdea.shopflow.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    val total: StateFlow<Double> = cartRepository.getCartItems().map { items ->
        items.sumOf { it.subtotal }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updateAddress(address: String) {
        _uiState.update { it.copy(address = address) }
    }

    fun updateCity(city: String) {
        _uiState.update { it.copy(city = city) }
    }

    fun updateZipCode(zipCode: String) {
        _uiState.update { it.copy(zipCode = zipCode) }
    }

    fun updateCardNumber(cardNumber: String) {
        _uiState.update { it.copy(cardNumber = cardNumber) }
    }

    fun placeOrder(onSuccess: () -> Unit) {
        if (!validateForm()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isProcessing = true) }
            delay(2000) // Simulate processing
            cartRepository.clearCart()
            _uiState.update { it.copy(isProcessing = false, orderPlaced = true) }
            onSuccess()
        }
    }

    private fun validateForm(): Boolean {
        val state = _uiState.value
        val errors = mutableListOf<String>()

        if (state.name.isBlank()) errors.add("Name is required")
        if (state.email.isBlank()) errors.add("Email is required")
        if (state.address.isBlank()) errors.add("Address is required")
        if (state.city.isBlank()) errors.add("City is required")
        if (state.zipCode.isBlank()) errors.add("Zip code is required")
        if (state.cardNumber.isBlank()) errors.add("Card number is required")

        _uiState.update { it.copy(errors = errors) }
        return errors.isEmpty()
    }
}

data class CheckoutUiState(
    val name: String = "",
    val email: String = "",
    val address: String = "",
    val city: String = "",
    val zipCode: String = "",
    val cardNumber: String = "",
    val isProcessing: Boolean = false,
    val orderPlaced: Boolean = false,
    val errors: List<String> = emptyList()
)
