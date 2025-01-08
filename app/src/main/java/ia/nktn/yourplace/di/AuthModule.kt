package ia.nktn.yourplace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ia.nktn.yourplace.data.auth.AuthRepository
import ia.nktn.yourplace.retrofit.ApiService

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    fun provideAuthRepository(apiService: ApiService): AuthRepository {
        return AuthRepository(apiService)
    }
}