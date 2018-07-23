package com.example.appName.domain

import io.reactivex.Single

interface FeedRepository {
    fun getFeedItems(): Single<List<FeedItem>>
}