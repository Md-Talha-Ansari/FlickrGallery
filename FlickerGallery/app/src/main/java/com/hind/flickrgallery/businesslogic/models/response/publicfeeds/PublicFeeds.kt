package com.hind.flickrgallery.businesslogic.models.response.publicfeeds

data class PublicFeeds(
    val description: String,
    val generator: String,
    val items: List<Feed>,
    val link: String,
    val modified: String,
    val title: String
)