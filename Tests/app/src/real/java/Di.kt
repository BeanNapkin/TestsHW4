package com.geekbrains.tests.repository

import com.geekbrains.tests.presenter.RepositoryContract
import com.geekbrains.tests.presenter.search.PresenterSearchContract
import com.geekbrains.tests.presenter.search.SearchPresenter
import com.geekbrains.tests.view.search.MainActivity
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Di {
    val mainModule = module{
        single { get<Retrofit>().create(GitHubApi::class.java) }

        single{ GitHubRepository(get()) as RepositoryContract }

        single { Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() }

        single { SearchPresenter(get()) as PresenterSearchContract }
    }
}