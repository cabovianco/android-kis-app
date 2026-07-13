package com.cabovianco.kis.data.remote.firebase.repository

import android.util.Log
import com.cabovianco.kis.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "FirebaseAuth"

class FirebaseAuthRepository @Inject constructor(
    val auth: FirebaseAuth
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
            Log.e(TAG, "signInWithEmailAndPassword:onFailure", ex)
            Result.failure(Exception("Failed to sign in with email and password"))
        }
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Log.i(TAG, "createUserWithEmailAndPassword:onSuccess")
                }
                .addOnFailureListener {
                    Log.i(TAG, "createUserWithEmailAndPassword:onFailure")
                }
                .await()

            Result.success(Unit)

        } catch (ex: Exception) {
            Log.e(TAG, "createUserWithEmailAndPassword:onFailure", ex)
            Result.failure(Exception("Failed to sign up with email and password"))
        }
    }
}
