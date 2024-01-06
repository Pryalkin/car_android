package com.bsuir.drozd.sources.backend

import com.bsuir.drozd.sources.model.api.ApiSource
import com.bsuir.drozd.sources.model.api.RetrofitApiSource
import com.bsuir.drozd.sources.model.auth.AuthSource
import com.bsuir.drozd.sources.model.auth.RetrofitAuthSource

class RetrofitSourcesProvider(
    private val config: RetrofitConfig
) : SourcesProvider {

    override fun getAuthSource(): AuthSource {
        return RetrofitAuthSource(config)
    }

    override fun getApiSource(): ApiSource {
        return RetrofitApiSource(config)
    }


}