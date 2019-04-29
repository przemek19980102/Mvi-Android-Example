package com.example.appName.common.exception

data class HttpException(val responseCode: Int) : Exception()