package com.cabovianco.kis.domain.repository

interface AuthRepository {
    fun isLoggedIn(): Boolean
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit>
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<Unit>
}
