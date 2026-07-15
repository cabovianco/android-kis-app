package com.cabovianco.kis.data.remote.firebase.repository

import android.util.Log
import com.cabovianco.kis.data.remote.firebase.firestore.Collection
import com.cabovianco.kis.data.remote.firebase.firestore.document.User
import com.cabovianco.kis.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "FirebaseAuthRepository"

class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Log.i(TAG, "signInWithEmailAndPassword:onSuccess")
                }
                .addOnFailureListener {
                    Log.i(TAG, "signInWithEmailAndPassword:onFailure")
                }
                .await()

            Result.success(Unit)

        } catch (ex: Exception) {
            Log.e(TAG, "signInWithEmailAndPassword:onException", ex)
            Result.failure(Exception("Failed to sign in with email and password"))
        }
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        username: String,
        password: String
    ): Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Log.i(TAG, "createUserWithEmailAndPassword:onSuccess")

                    val uid = auth.currentUser?.uid ?: throw Exception("User not found")

                    firestore.collection(Collection.USER.path)
                        .document(uid)
                        .set(
                            User(
                                email = email,
                                username = username
                            )
                        )
                        .addOnSuccessListener {
                            Log.i(
                                TAG,
                                "users/${uid}: $username added successfully"
                            )
                        }
                        .addOnFailureListener { Log.i(TAG, "Failed to add user $username") }
                }
                .addOnFailureListener {
                    Log.i(TAG, "createUserWithEmailAndPassword:onFailure")
                }
                .await()

            Result.success(Unit)

        } catch (ex: Exception) {
            Log.e(TAG, "createUserWithEmailAndPassword:onException", ex)
            Result.failure(Exception("Failed to sign up with email and password"))
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            auth.signOut()
            Result.success(Unit)

        } catch (ex: Exception) {
            Log.e(TAG, "signOut:onException", ex)
            Result.failure(Exception("Failed to sign out"))
        }
    }
}
