package com.cabovianco.kis.domain.repository

import com.cabovianco.kis.domain.model.SecretComposeItem
import com.cabovianco.kis.domain.model.SecretItem
import kotlinx.coroutines.flow.Flow

interface SecretRepository {
    suspend fun send(secret: SecretComposeItem): Result<Unit>
    fun getAll(): Flow<Result<List<SecretItem>>>
}
