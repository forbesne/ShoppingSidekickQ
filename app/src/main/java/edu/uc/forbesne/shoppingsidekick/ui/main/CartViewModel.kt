package edu.uc.forbesne.shoppingsidekick.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uc.forbesne.shoppingsidekick.dto.CartItem

class CartViewModel : MainViewModel() {
    private var _cartItem = MutableLiveData<List<CartItem>>()

    internal fun fetchCartItem() {
        var cartCollection = firestore.collection("cart")
        cartCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            var innerCartItems = querySnapshot?.toObjects(CartItem::class.java)
            _cartItem.postValue(innerCartItems!!)
        }
    }

    internal var cartItem : MutableLiveData<List<CartItem>>
        get() { return _cartItem}
        set(value) {_cartItem = value}
}