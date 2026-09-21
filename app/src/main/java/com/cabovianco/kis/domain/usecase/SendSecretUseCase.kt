package com.cabovianco.kis.domain.usecase

import com.cabovianco.kis.domain.model.SecretComposeItem
import com.cabovianco.kis.domain.repository.SecretRepository
import javax.inject.Inject

class SendSecretUseCase @Inject constructor(
    private val secretRepository: SecretRepository
) {
    suspend operator fun invoke(secret: SecretComposeItem): Result<Unit> =
        secretRepository.send(secret)
}
