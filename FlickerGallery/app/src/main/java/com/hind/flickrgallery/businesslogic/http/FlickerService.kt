package com.hind.flickrgallery.businesslogic.http

import com.hind.flickrgallery.businesslogic.models.request.publicfeeds.PublicFeedsRequest
import com.hind.flickrgallery.businesslogic.models.response.publicfeeds.PublicFeeds
import retrofit2.Call

object FlickerService {
    private val _service = FlickrServiceBuilder.buildService(FlickrServiceEndpoints::class.java)

    fun getPublicFeeds(request: PublicFeedsRequest): Call<PublicFeeds> {
        return _service.getPublicFeeds(request.toMap())
    }
}