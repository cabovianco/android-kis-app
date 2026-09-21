package com.cabovianco.kis.domain.usecase.auth

import com.cabovianco.kis.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> =
        authRepository.signInWithEmailAndPassword(email, password)
}
