package com.bsuir.drozd

import android.content.Context
import androidx.fragment.app.Fragment
import com.bsuir.drozd.app.repository.ApiRepository
import com.bsuir.drozd.app.repository.AuthRepository
import com.bsuir.drozd.app.screens.Navigator
import com.bsuir.drozd.app.setting.AppSettings
import com.bsuir.drozd.app.setting.SharedPreferencesAppSettings
import com.bsuir.drozd.sources.SourceProviderHolder
import com.bsuir.drozd.sources.backend.SourcesProvider
import com.bsuir.drozd.sources.model.api.ApiSource
import com.bsuir.drozd.sources.model.auth.AuthSource

object Singletons {

    private lateinit var appContext: Context

    private val sourcesProvider: SourcesProvider by lazy {
        SourceProviderHolder.sourcesProvider
    }

    val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(appContext)
    }

    // source
    private val authSource: AuthSource by lazy {
        sourcesProvider.getAuthSource()
    }

    private val apiSource: ApiSource by lazy {
        sourcesProvider.getApiSource()
    }

    // repository
    val authRepository: AuthRepository by lazy {
        AuthRepository(
            authSource = authSource,
            appSettings = appSettings
        )
    }

    val apiRepository: ApiRepository by lazy {
        ApiRepository(
            apiSource = apiSource,
            appSettings = appSettings
        )
    }


    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }

    fun Fragment.navigator() = requireActivity() as Navigator
}