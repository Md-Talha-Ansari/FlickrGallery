package com.hind.flickrgallery.ui.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.hind.flickrgallery.R
import com.hind.flickrgallery.businesslogic.models.response.publicfeeds.Feed
import com.hind.flickrgallery.databinding.ActivityPublicFeedsBinding
import com.hind.flickrgallery.ui.adapters.FeedsViewAdapter
import com.hind.flickrgallery.ui.viewmodels.PublicFeedsViewModel

class PublicFeedsActivity : AppCompatActivity(),FeedsViewAdapter.ItemClickListener {

    private var _feedsAdapter = FeedsViewAdapter(emptyList())
    private lateinit var _viewModel:PublicFeedsViewModel
    private lateinit var _binding:ActivityPublicFeedsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_public_feeds)
        _viewModel = ViewModelProvider(this).get(PublicFeedsViewModel::class.java)
        //Configure views and layouts
        configureSwipeLayout()
        configureFeedsLayout()
        //Configure live data observers
        configureDataObservers()
        //Fetch the feeds data
        _viewModel.fetchFeeds()
    }

    private fun configureSwipeLayout(){
        _binding.swipeLayout.setOnRefreshListener {
            Toast.makeText(this,"Refreshing", Toast.LENGTH_SHORT).show()
            _viewModel.fetchFeeds()
        }
    }

    private fun configureFeedsLayout(){
        _binding.adapter = _feedsAdapter
        val layoutManager = GridLayoutManager(this,2)
        _binding.publicFeeds.layoutManager = layoutManager
    }

    private fun configureDataObservers(){
        _viewModel.feeds.observe(this, Observer(_feedsAdapter::setFeeds))
        _viewModel.isFetching.observe(this, { _binding.swipeLayout.isRefreshing = it })
    }

    override fun onResume() {
        super.onResume()
        _feedsAdapter.setItemClickListener(this)
    }

    override fun onItemClick(item: Feed) {
        _feedsAdapter.setItemClickListener(null)
//        val intent = Intent(this,FeedDetailsActivity::class.java)
//        intent.putExtra("feed", Gson().toJson(item))
//        startActivity(intent)
    }

}