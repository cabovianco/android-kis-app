package com.cabovianco.kis.data.remote.firebase.repository

import android.util.Log
import com.cabovianco.kis.data.remote.firebase.firestore.Collection
import com.cabovianco.kis.data.remote.firebase.firestore.document.Secret
import com.cabovianco.kis.domain.model.SecretComposeItem
import com.cabovianco.kis.domain.model.SecretItem
import com.cabovianco.kis.domain.repository.SecretRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "FirebaseSecretRepository"

class FirebaseSecretRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : SecretRepository {
    override suspend fun send(secret: SecretComposeItem): Result<Unit> {
        return try {
            val to = secret.to
            val content = secret.content
            val from = findUsername()

            val secret = Secret(
                from = from,
                content = content
            )

            firestore.collection(Collection.INBOX.path)
                .document(to)
                .collection(Collection.RECEIVED.path)
                .add(secret)
                .addOnSuccessListener {
                    Log.i(TAG, "Secret sent successfully")
                }
                .addOnFailureListener {
                    Log.i(TAG, " Failed to send secret: ${it.message}")
                }
                .await()

            Result.success(Unit)

        } catch (ex: Exception) {
            Log.e(TAG, "Failed to send secret", ex)
            Result.failure(ex)
        }
    }

    override fun getAll(): Flow<Result<List<SecretItem>>> = flow {
        emit(
            try {
                val username = findUsername()

                val snapshot = firestore.collection(Collection.INBOX.path)
                    .document(username)
                    .collection(Collection.RECEIVED.path)
                    .get()
                    .await()

                val items = snapshot.documents.map {
                    SecretItem(
                        content = it.getString("content") ?: "",
                        from = it.getString("from") ?: ""
                    )
                }

                Log.i(TAG, "Fetched ${items.size} secrets for user $username")

                Result.success(items)

            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch secrets", ex)
                Result.failure(ex)
            }
        )
    }

    private suspend fun findUsername(): String {
        val uid = auth.currentUser?.uid ?: throw Exception("User not found")

        val snapshot = firestore.collection(Collection.USER.path)
            .document(uid)
            .get()
            .await()

        return snapshot.getString("username") ?: throw Exception("Username not found")
    }
}
