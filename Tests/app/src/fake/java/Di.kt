package com.geekbrains.tests.repository

import FakeGitHubRepository
import com.geekbrains.tests.presenter.RepositoryContract
import com.geekbrains.tests.presenter.search.PresenterSearchContract
import com.geekbrains.tests.presenter.search.SearchPresenter
import org.koin.dsl.module

object Di {
    val mainModule = module{
        single{ FakeGitHubRepository() as RepositoryContract }
        single { SearchPresenter(get()) as PresenterSearchContract}
    }
}
