package com.cabovianco.kis.di

import com.cabovianco.kis.data.remote.firebase.repository.FirebaseSecretRepository
import com.cabovianco.kis.domain.repository.SecretRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SecretModule {
    @Binds
    @Singleton
    abstract fun bindSecretRepository(impl: FirebaseSecretRepository): SecretRepository
}
