package com.hind.flickrgallery.businesslogic.http

import com.hind.flickrgallery.businesslogic.models.request.publicfeeds.PublicFeedsRequest
import com.hind.flickrgallery.businesslogic.models.response.publicfeeds.PublicFeeds
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FlickrServiceEndpoints {

    @JvmSuppressWildcards
    @GET("feeds/photos_public.gne?nojsoncallback=1")
    fun getPublicFeeds(@QueryMap query:Map<String,Any>):Call<PublicFeeds>

}