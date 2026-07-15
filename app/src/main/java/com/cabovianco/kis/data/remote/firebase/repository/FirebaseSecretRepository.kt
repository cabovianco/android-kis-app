package com.cabovianco.kis.data.remote.firebase.repository

import android.util.Log
import com.cabovianco.kis.data.remote.firebase.firestore.Collection
import com.cabovianco.kis.data.remote.firebase.firestore.document.Secret
import com.cabovianco.kis.domain.model.SecretComposeItem
import com.cabovianco.kis.domain.model.SecretItem
import com.cabovianco.kis.domain.repository.SecretRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
                    Log.i(TAG, "send:onSuccess")
                }
                .addOnFailureListener {
                    Log.i(TAG, "send:onFailure")
                }
                .await()

            Result.success(Unit)

        } catch (ex: Exception) {
            Log.e(TAG, "send:onException", ex)
            Result.failure(Exception("An error occurred while sending the secret"))
        }
    }

    override fun getAll(): Flow<Result<List<SecretItem>>> = callbackFlow {
        val username = try {
            findUsername()
        } catch (ex: Exception) {
            Log.e(TAG, "getAll:onFindUsernameException", ex)
            trySend(Result.failure(ex))
            return@callbackFlow
        }

        val listener = firestore.collection(Collection.INBOX.path)
            .document(username)
            .collection(Collection.RECEIVED.path)
            .addSnapshotListener { snapshots, exception ->
                if (exception != null) {
                    Log.e(TAG, "getAll:onSnapshotException", exception)
                    trySend(Result.failure(exception))
                    return@addSnapshotListener
                }

                val items = snapshots?.documents?.map {
                    SecretItem(
                        id = it.id,
                        content = it.getString("content") ?: "",
                        from = it.getString("from") ?: ""
                    )
                } ?: emptyList()

                trySend(Result.success(items))
            }

        awaitClose { listener.remove() }
    }

    override suspend fun delete(secret: SecretItem) {
        try {
            val username = findUsername()

            firestore.collection(Collection.INBOX.path)
                .document(username)
                .collection(Collection.RECEIVED.path)
                .document(secret.id)
                .delete()
                .addOnSuccessListener {
                    Log.i(TAG, "delete:onSuccess")
                }
                .addOnFailureListener {
                    Log.i(TAG, "delete:onFailure")
                }
                .await()

        } catch (ex: Exception) {
            Log.e(TAG, "delete:onException", ex)
        }
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
