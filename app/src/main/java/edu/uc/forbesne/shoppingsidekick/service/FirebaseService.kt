package edu.uc.forbesne.shoppingsidekick.service

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import edu.uc.forbesne.shoppingsidekick.dto.Cart
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
import kotlin.reflect.KFunction0

class FirebaseService {
    private val cart: Cart = Cart()

    // To enable testing
    // removed firebase instances from being created automatically when class object is created
    fun initialize(){
        getCartFromFirebase()
    }

    fun getCart(): Cart{
        return cart
    }

    fun getFireBaseInstance(): FirebaseFirestore{
        val db = FirebaseFirestore.getInstance()
        return db
    }

    fun getCartFromFirebase() {
        val db = FirebaseFirestore.getInstance()

        db.collection("cart").addSnapshotListener { snapshot, e ->
            // if there is an exception we want to skip.
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen Failed", e)
                return@addSnapshotListener
            }

            // if we are here, we did not encounter an exception
            if (snapshot != null) {
                val documents = snapshot.documents

                cart.emptyCart()

                documents.forEach {

                    val cartItem = it.toObject(CartItem::class.java)
                    if (cartItem != null) {
                        // Sets id to the one firebase creates. used to updtate item's quantity in firebase
                        cartItem.id = it.id
                        cart.addItem(cartItem)
                    }
                }
            }
        }
    }

    fun addCartItemToFirebase(cartItem: CartItem): String {
        val db = FirebaseFirestore.getInstance()

        val document = db.collection("cart").document()
        val cartItemId = document.id
        cartItem.id = cartItemId

        document.set(cartItem)
            .addOnSuccessListener {
                Log.d("Firebase", "document saved")
            }
            .addOnFailureListener {
                Log.d("Firebase", "Save Failed")
            }

        return cartItemId
    }

    fun adjustCartItemQuantityInFirebase(existingCartItem: CartItem, quantityToAdd: Int) {
        val db = FirebaseFirestore.getInstance()
        existingCartItem.quantity += quantityToAdd

        db.collection("cart")
            .document(existingCartItem.id)
            .set(existingCartItem)
            .addOnSuccessListener {
                Log.d("Firebase", "document saved")
            }
            .addOnFailureListener {
                Log.d("Firebase", "Save Failed")
            }
    }

    fun emptyCart(){

        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("cart").get().addOnSuccessListener { querySnapshot ->
            for (documentSnapshot in querySnapshot) {
                firestore.collection("cart").document(documentSnapshot.id)
                        .delete()
                        .addOnSuccessListener {
                            Log.d(
                                    "Firebase",
                                    "DocumentSnapshot successfully deleted!"
                            )
                        }
                        .addOnFailureListener { e -> Log.w("Firebase", "Error deleting document", e) }
            }
        }.addOnFailureListener { e -> Log.w("Firebase", "Error deleting documents", e)}
    }

    fun removeItemFromCart(cartItem: CartItem, callback: KFunction0<Unit>){
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("cart").document(cartItem.id)
                .delete()
                .addOnSuccessListener {
                    callback()
                    Log.d(
                            "Firebase",
                            "DocumentSnapshot successfully deleted!"
                    )
                }
                .addOnFailureListener { e -> Log.w("Firebase", "Error deleting document", e) }
    }

    // Moved here from CartViewModel -2 (last)
    internal fun fetchCartItem(cartItem: MutableLiveData<List<CartItem>>) {
        val firestore = FirebaseFirestore.getInstance()

        var cartCollection = firestore.collection("cart")
        cartCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            var innerCartItems = querySnapshot?.toObjects(CartItem::class.java)
            cartItem.postValue(innerCartItems!!)
        }
    }
}