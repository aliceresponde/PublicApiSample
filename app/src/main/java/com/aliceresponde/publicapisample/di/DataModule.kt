package com.aliceresponde.publicapisample.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.aliceresponde.publicapisample.data.datasource.LocalDataSource
import com.aliceresponde.publicapisample.data.datasource.RemoteDataSource
import com.aliceresponde.publicapisample.data.local.AppDatabase
import com.aliceresponde.publicapisample.data.local.RoomDataSource
import com.aliceresponde.publicapisample.data.remote.NetworkConnection
import com.aliceresponde.publicapisample.data.remote.NetworkConnectionInterceptor
import com.aliceresponde.publicapisample.data.remote.RetrofitDataSource
import com.aliceresponde.publicapisample.data.remote.YelpApiService
import com.aliceresponde.publicapisample.data.repository.BusinessRepository
import com.aliceresponde.publicapisample.data.repository.BusinessRepositoryImp
import com.aliceresponde.publicapisample.domain.GetBusinessDetailUseCase
import com.aliceresponde.publicapisample.domain.GetBusinessDetailUseCaseImp
import com.aliceresponde.publicapisample.domain.GetBusinessUseCase
import com.aliceresponde.publicapisample.domain.GetBusinessUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "database-name").build()

    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context): Interceptor =
        NetworkConnectionInterceptor(context)

    @Provides
    @Singleton
    fun provideApiService(interceptor: Interceptor) = YelpApiService.invoke(interceptor)

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun provideNetworkConnection(
        @ApplicationContext context: Context,
        connectivityManager: ConnectivityManager
    ) = NetworkConnection(context, connectivityManager)

    //    ----------------------- Repository----------------------

    @Provides
    @Singleton
    fun provideLocalDataSource(db: AppDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    @Singleton
    fun provideRemoteDataSource(service: YelpApiService): RemoteDataSource =
        RetrofitDataSource(service)

    @Provides
    @Singleton
    fun provideCounterRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): BusinessRepository = BusinessRepositoryImp(localDataSource, remoteDataSource)

    //    ----------------------- UseCase-------------------------
    @Provides
    fun provideGetBusinessUseCase(repository: BusinessRepository): GetBusinessUseCase =
        GetBusinessUseCaseImp(repository)

    @Provides
    fun providesGetBusinessDetailUseCase(repository: BusinessRepository): GetBusinessDetailUseCase =
        GetBusinessDetailUseCaseImp(repository)

}