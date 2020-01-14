package com.zup.appkoin.di

import com.zup.appkoin.repository.DataRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { DataRepository(moviesApi = get()) }
}
