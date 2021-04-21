package edu.uc.forbesne.shoppingsidekick.service

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import edu.uc.forbesne.shoppingsidekick.dto.Cart
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
import kotlin.reflect.KFunction0

/**
 * Service class that deals with Firebase calls, and provides firebase instances to callers.
 * This class idea was a result of a merge with @atharva106 Code Review 2
 *
 * @param cart  user cart from firbase.
 */
class FirebaseService {
    private val cart: Cart = Cart()

    // To enable testing
    // removes firebase instances from being created at class level automatically
    // when class object is created into functions that get called
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
        var authInstance = FirebaseAuth.getInstance()
        var user = ""
        if(authInstance!=null) {
            var firbaseUser = authInstance.currentUser
            if(firbaseUser!=null) {
                user = firbaseUser.uid
            }
        }
        if(user==""){
            user = "cart"
        }
        db.collection(user).addSnapshotListener { snapshot, e ->
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
        var authInstance = FirebaseAuth.getInstance()
        var user = ""
        if(authInstance!=null) {
            var firbaseUser = authInstance.currentUser
            if(firbaseUser!=null) {
                user = firbaseUser.uid
            }
        }
        if(user==""){
            user = "cart"
        }
        val document = db.collection(user).document()
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
        var authInstance = FirebaseAuth.getInstance()
        var user = ""
        if(authInstance!=null) {
            var firbaseUser = authInstance.currentUser
            if(firbaseUser!=null) {
                user = firbaseUser.uid
            }
        }
        if(user==""){
            user = "cart"
        }
        db.collection(user)
                .document(existingCartItem.id)
                .set(existingCartItem)
                .addOnSuccessListener {
                    Log.d("Firebase", "document saved")
                }
                .addOnFailureListener {
                    Log.d("Firebase", "Save Failed")
                }
    }

    // This function is based on the @Hamilsu code review2
    fun emptyCart(){

        val firestore = FirebaseFirestore.getInstance()
        var authInstance = FirebaseAuth.getInstance()
        var user = ""
        if(authInstance!=null) {
            var firbaseUser = authInstance.currentUser
            if(firbaseUser!=null) {
                user = firbaseUser.uid
            }
        }
        if(user==""){
            user = "cart"
        }
        firestore.collection(user).get().addOnSuccessListener { querySnapshot ->
            for (documentSnapshot in querySnapshot) {
                firestore.collection(user).document(documentSnapshot.id)
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
        var authInstance = FirebaseAuth.getInstance()
        var user = ""
        if(authInstance!=null) {
            var firbaseUser = authInstance.currentUser
            if(firbaseUser!=null) {
                user = firbaseUser.uid
            }
        }
        if(user==""){
            user = "cart"
        }
        firestore.collection(user).document(cartItem.id)
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
        var authInstance = FirebaseAuth.getInstance()
        var user = ""
        if(authInstance!=null) {
            var firbaseUser = authInstance.currentUser
            if(firbaseUser!=null) {
                user = firbaseUser.uid
            }
        }
        if(user==""){
            user = "cart"
        }
        var cartCollection = firestore.collection(user)
        cartCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            var innerCartItems = querySnapshot?.toObjects(CartItem::class.java)
            cartItem.postValue(innerCartItems!!)
        }
    }
}