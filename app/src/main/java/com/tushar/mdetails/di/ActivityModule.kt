package com.tushar.mdetails.di

import com.tushar.mdetails.ui.moviedetails.CastAdapter
import com.tushar.mdetails.ui.moviedetails.CrewAdapter
import com.tushar.mdetails.ui.moviedetails.SimilarMovieAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * This Class provides all the dependencies which are scoped to [MovieScope] and are available in the MainActivity class.
 */
@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @MovieScope
    @Provides
    fun provideCastAdapter() = CastAdapter()

    @MovieScope
    @Provides
    fun provideCrewAdapter() = CrewAdapter()

    @MovieScope
    @Provides
    fun provideSimilarMoviesAdapter() = SimilarMovieAdapter()

}