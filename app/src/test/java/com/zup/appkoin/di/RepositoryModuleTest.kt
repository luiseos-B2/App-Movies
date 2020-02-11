package com.zup.appkoin.di

import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules

class RepositoryModuleTest : AutoCloseKoinTest() {

    @Test
    fun shouldInjectMyComponentsMoviesApi() {
        startKoin {
            modules((listOf(repositoryModule, viewModelModule, appModule)))
        }.checkModules()
    }

}