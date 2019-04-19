package com.example.appName.common.exceptions

data class HttpException(val responseCode: Int) : Exception()