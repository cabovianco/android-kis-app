package com.cabovianco.kis.domain.usecase

import com.cabovianco.kis.domain.model.SecretItem
import com.cabovianco.kis.domain.repository.SecretRepository
import javax.inject.Inject

class DeleteSecretUseCase @Inject constructor(
    private val secretRepository: SecretRepository
) {
    suspend operator fun invoke(secret: SecretItem) = secretRepository.delete(secret)
}
