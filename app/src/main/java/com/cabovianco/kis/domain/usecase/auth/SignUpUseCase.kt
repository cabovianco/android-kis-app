package com.cabovianco.kis.domain.usecase.auth

import com.cabovianco.kis.domain.repository.AuthRepository
import javax.inject.Inject

private const val PASSWORD_LENGTH = 6

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return if (password.length < PASSWORD_LENGTH)
            Result.failure(Exception("Password must be at least $PASSWORD_LENGTH characters long"))
        else authRepository.signUpWithEmailAndPassword(email, password)
    }
}
