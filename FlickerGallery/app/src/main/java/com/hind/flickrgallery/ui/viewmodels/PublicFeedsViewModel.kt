package com.hind.flickrgallery.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hind.flickrgallery.businesslogic.http.FlickerService
import com.hind.flickrgallery.businesslogic.http.FlickrServiceBuilder
import com.hind.flickrgallery.businesslogic.http.FlickrServiceEndpoints
import com.hind.flickrgallery.businesslogic.models.request.publicfeeds.PublicFeedsRequest
import com.hind.flickrgallery.businesslogic.models.response.publicfeeds.Feed
import com.hind.flickrgallery.businesslogic.models.response.publicfeeds.PublicFeeds
import com.hind.flickrgallery.ui.adapters.FeedsViewAdapter
import com.hind.flickrgallery.ui.screens.UIConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PublicFeedsViewModel : ViewModel() {
    private val _publicFeeds = MutableLiveData<PublicFeeds>()
    private val _feeds = MutableLiveData<List<Feed>>(emptyList())
    val feeds:LiveData<List<Feed>> = _feeds
    private val _isFetching = MutableLiveData(false)
    val isFetching:LiveData<Boolean> = _isFetching
    private val _refereshStatus = MutableLiveData<Int>()
    val refereshStatus:LiveData<Int> = _refereshStatus

    /**
     * Method to fetch latest feeds from server asynchronously.
     */
    fun fetchFeeds(){
        //Set isFetching to true to show spinner
        if(_isFetching.value == true){
            return
        }
        _refereshStatus.value = UIConstants.STATE_IDEAL
        _isFetching.value = true
        //Create public feeds request
        val request = PublicFeedsRequest(null,null,tags = null)
        //Call flicker public feeds service
        val call = FlickerService.getPublicFeeds(request)
        call.enqueue(object : Callback<PublicFeeds> {
            override fun onResponse(call: Call<PublicFeeds>, response: Response<PublicFeeds>) {
                if(response.isSuccessful){//Request successful update feeds
                    Log.d("Public feeds success : ", response.body().toString())
                    _publicFeeds.value = response.body()
                    _feeds.value = _publicFeeds.value?.items
                    _refereshStatus.value = UIConstants.FEEDS_REFERESHED
                }else{
                    //Request failed show error message
                    Log.d("Public feeds error : ", response.errorBody().toString())
                    _refereshStatus.value = UIConstants.ERROR_CANNOT_REFRESH_FEEDS
                }
                _isFetching.value = false
            }
            override fun onFailure(call: Call<PublicFeeds>, t: Throwable) {
                //Exception occurred notify user
                Log.d("Public feeds failure: ", t.localizedMessage)
                _isFetching.value = false
                _refereshStatus.value = UIConstants.ERROR_CANNOT_REFRESH_FEEDS
            }
        })
    }
}