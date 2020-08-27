package com.mendelin.catpedia.data_access_layer.server

object ServerConfig {

    init {
        System.loadLibrary("data-wrapper")
    }
    external fun getBaseUrl(): String
    external fun getApiHeader(): String
    external fun getApiKey(): String

    external fun getMockedUserName(): String
    external fun getMockedUserPassword(): String
}