package com.aliceresponde.publicapisample.di

import com.aliceresponde.publicapisample.domain.GetBusinessUseCase
import com.aliceresponde.publicapisample.ui.search.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object SearchFragmentModule {

    @Provides
    fun providesSearchViewModel(useCase: GetBusinessUseCase) = SearchViewModel(useCase)
}
