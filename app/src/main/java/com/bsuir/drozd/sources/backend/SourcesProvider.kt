package com.bsuir.drozd.sources.backend

import com.bsuir.drozd.sources.model.api.ApiSource
import com.bsuir.drozd.sources.model.auth.AuthSource

interface SourcesProvider {

    fun getAuthSource(): AuthSource
    fun getApiSource(): ApiSource

}