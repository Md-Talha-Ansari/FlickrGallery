package com.hind.flickrgallery.businesslogic.models.response.publicfeeds

data class Feed(
    val author: String,
    val author_id: String,
    val date_taken: String,
    val description: String,
    val link: String,
    val media: Media,
    val published: String,
    val tags: String,
    val title: String
)