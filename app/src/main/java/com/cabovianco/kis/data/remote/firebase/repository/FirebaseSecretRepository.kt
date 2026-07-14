package com.cabovianco.kis.data.remote.firebase.repository

import com.cabovianco.kis.domain.model.SecretComposeItem
import com.cabovianco.kis.domain.repository.SecretRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseSecretRepository @Inject constructor(

) : SecretRepository {
    override suspend fun send(secret: SecretComposeItem): Result<Unit> {
        withContext(Dispatchers.IO) {
            Thread.sleep(2000)
        }

        return Result.success(Unit)
    }
}
