package com.cabovianco.kis.domain.repository

import com.cabovianco.kis.domain.model.SecretComposeItem

interface SecretRepository {
    suspend fun send(secret: SecretComposeItem): Result<Unit>
}
