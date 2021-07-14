package com.hind.flickrgallery

import android.util.Log
import com.hind.flickrgallery.businesslogic.http.FlickerService
import com.hind.flickrgallery.businesslogic.http.FlickrServiceBuilder
import com.hind.flickrgallery.businesslogic.http.FlickrServiceEndpoints
import com.hind.flickrgallery.businesslogic.models.request.publicfeeds.PublicFeedsRequest
import org.junit.Test
import java.io.IOException

class FlickerServiceEndpointsUnitTests {
    private val TAG = "FlickerServiceEndpointsUnitTests"
    @Test
    fun publicFeedsSuccessTest(){

        val request = PublicFeedsRequest(null,null,null)
        val call = FlickerService.getPublicFeeds(request)
        try{
            val resposne = call.execute()
            if(resposne.isSuccessful && resposne.code() == 200){
                assert(resposne.body() != null){
                    Log.d(TAG, resposne.body().toString())
                }
            }
        }catch (e:IOException){
            Log.d(TAG,e.localizedMessage?:"Public feeds fetch failed.")
        }
    }

    @Test
    fun publicFeedsFailureTest(){

        val request = PublicFeedsRequest(null,null,null)
        val call = FlickerService.getPublicFeeds(request)
        try{
            val resposne = call.execute()
            if(!resposne.isSuccessful || resposne.code() != 200){
                assert(resposne.body() != null){
                    Log.d(TAG, resposne.body().toString())
                }
            }
        }catch (e:IOException){
            Log.d(TAG,e.localizedMessage?:"Public feeds fetch failed.")
        }
    }

}