package com.hind.flickrgallery.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hind.flickrgallery.businesslogic.models.response.publicfeeds.Feed

class PublicFeedsItemViewModel(item: Feed) {
    private val _feed = MutableLiveData(item)
    val feed:LiveData<Feed> = _feed
}