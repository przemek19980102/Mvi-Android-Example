package com.example.appName.domain.exceptions

data class HttpException(val responseCode: Int) : Exception()