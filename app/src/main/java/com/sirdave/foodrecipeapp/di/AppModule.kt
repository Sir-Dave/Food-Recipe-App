package com.sirdave.foodrecipeapp.di

import android.app.Application
import com.sirdave.foodrecipeapp.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Application): BaseApplication{
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun someRandomString(): String{
        return "Some random string: æwådnlcøøwec'"
    }
}