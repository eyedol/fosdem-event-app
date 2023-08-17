/*
 * Copyright 2022 Addhen Limited
 */
package com.findreels.data.sqldelight

import android.content.Context
import com.findreels.data.sqldelight.api.database.AppSqlDelightDatabase
import com.findreels.data.sqldelight.api.database.Movie
import com.findreels.data.sqldelight.api.database.SqlDriverFactory
import com.findreels.data.sqldelight.database.AndroidSqlDelightDriverFactory
import com.findreels.data.sqldelight.database.MovieCastAdapter
import com.findreels.data.sqldelight.database.MovieDirectorAdapter
import com.findreels.data.sqldelight.database.MovieGenreAdapter
import com.findreels.data.sqldelight.database.MovieMediaAdapter
import com.findreels.data.sqldelight.database.MovieReviewAdapter
import com.findreels.data.sqldelight.database.MovieScheduleAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class SqlDelightDataModule {

  @Provides
  fun providesReviewDriverFactory(@ApplicationContext appContext: Context): SqlDriverFactory {
    return AndroidSqlDelightDriverFactory(appContext)
  }

  @Provides
  internal fun provideAppDatabase(sqlDriverFactory: SqlDriverFactory): AppSqlDelightDatabase {
    return AppSqlDelightDatabase(
      driver = sqlDriverFactory.createDriver(),
      movieAdapter = Movie.Adapter(
        directorsAdapter = MovieDirectorAdapter,
        castsAdapter = MovieCastAdapter,
        mediaAdapter = MovieMediaAdapter,
        schedulesAdapter = MovieScheduleAdapter,
        reviewsAdapter = MovieReviewAdapter,
        genresAdapter = MovieGenreAdapter,
      ),
    )
  }
}
