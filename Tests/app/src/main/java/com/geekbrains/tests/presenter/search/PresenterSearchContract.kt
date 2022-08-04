package com.geekbrains.tests.presenter.search

import com.geekbrains.tests.presenter.PresenterContract
import com.geekbrains.tests.view.ViewContract
import com.geekbrains.tests.view.search.ViewSearchContract

internal interface PresenterSearchContract : PresenterContract {
    fun searchGitHub(searchQuery: String)
    fun attach(view: ViewSearchContract)
    fun detach()
}
