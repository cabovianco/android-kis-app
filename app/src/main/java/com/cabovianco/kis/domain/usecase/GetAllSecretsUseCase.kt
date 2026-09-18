package com.cabovianco.kis.domain.usecase

import com.cabovianco.kis.domain.model.SecretItem
import com.cabovianco.kis.domain.repository.SecretRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSecretsUseCase @Inject constructor(
    private val secretRepository: SecretRepository
) {
    operator fun invoke(): Flow<Result<List<SecretItem>>> = secretRepository.getAll()
}
