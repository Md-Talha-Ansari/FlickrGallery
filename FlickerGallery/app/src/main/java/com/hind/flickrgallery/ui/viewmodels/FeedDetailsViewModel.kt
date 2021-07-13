package com.hind.flickrgallery.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hind.flickrgallery.businesslogic.models.response.publicfeeds.Feed

class FeedDetailsViewModel : ViewModel() {
    private val _feedDetails = MutableLiveData<Feed>()
    val feedDetails:LiveData<Feed> = _feedDetails

    fun setFeedDetails(feed:Feed){
        _feedDetails.value = feed
    }
}