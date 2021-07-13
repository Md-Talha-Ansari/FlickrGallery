package com.hind.flickrgallery.ui.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.hind.flickrgallery.R
import com.hind.flickrgallery.businesslogic.models.response.publicfeeds.Feed
import com.hind.flickrgallery.databinding.ActivityFeedDetailsBinding
import com.hind.flickrgallery.ui.viewmodels.FeedDetailsViewModel

class FeedDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel:FeedDetailsViewModel

    private lateinit var _binding:ActivityFeedDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_feed_details)
        initViewModel()
        initViews()
    }

    /**
     * Initialize the view model.
     */
    private fun initViewModel(){
        //Read the feed info passed from previous activity.
        val feedInfo =  intent.getStringExtra(UIConstants.INTENT_KEY_FEED)
        val feed = Gson().fromJson(feedInfo,Feed::class.java)
        //Initialize & Set feed detail to view model.
        viewModel = ViewModelProvider(this).get(FeedDetailsViewModel::class.java)
        viewModel.setFeedDetails(feed)
    }

    /**
     * Initialize view components.
     */
    private fun initViews(){
        _binding.viewModel = viewModel
        //Set title to feed title if feed have a title
        if(viewModel.feedDetails.value != null){
            title = viewModel.feedDetails.value?.title
        }
    }
}