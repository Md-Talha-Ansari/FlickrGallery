package com.hind.flickrgallery.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.hind.flickrgallery.R
import com.hind.flickrgallery.businesslogic.models.response.publicfeeds.Feed
import com.hind.flickrgallery.databinding.ActivityFeedDetailsBinding
import com.hind.flickrgallery.ui.viewmodels.FeedDetailsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FeedDetailsActivity : AppCompatActivity() {

    private lateinit var _viewModel:FeedDetailsViewModel

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
        _viewModel = ViewModelProvider(this).get(FeedDetailsViewModel::class.java)
        _viewModel.setFeedDetails(feed)
        _viewModel.fileWriteStatus.observe(this, Observer {
            if(it == UIConstants.ERROR_CANNOT_SAVE_IMAGE){
                Toast.makeText(this,R.string.file_not_saved,Toast.LENGTH_SHORT).show()
            }else if(it == UIConstants.FILE_SAVED){
                Toast.makeText(this,R.string.file_saved,Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Initialize view components.
     */
    private fun initViews(){
        _binding.viewModel = _viewModel
        //Set title to feed title if feed have a title
        val feedTitle = _viewModel.feedDetails.value?.title
        if(feedTitle != null){
             title = feedTitle
        }
    }

    //Storage permission request
    private val request = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
            _binding.btnSaveImage.isEnabled = true
            //save image to external storage
            saveImage()
        }else{
            _binding.btnSaveImage.isEnabled = true
        }
    }

    fun onSaveImage(view: View) {
        _binding.btnSaveImage.isEnabled = false
        if(PermissionChecker.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED){
            //save image to storage
            _binding.btnSaveImage.isEnabled = true
            saveImage()
        }else{

            AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.storage_permission_required)
                .setPositiveButton(R.string.allow){ dialog,which ->
                    request.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                .setNegativeButton(R.string.decline){ dialog,which->
                    _binding.btnSaveImage.isEnabled = true
                }.show()

        }
    }

    /**
     * Method to save image.
     */
    private fun saveImage(){
        GlobalScope.launch {
            _viewModel.saveImage(_binding.image.drawable.toBitmap(),contentResolver)
        }
    }

    /**
     * Click listener for onEmail button.
     * @param view OnEmail button.
     */
    fun onEmail(view: View) {

    }

    /**
     * Open current feed image in browser.
     * @param view openInBrowserButton.
     */
    fun onOpenInBrowser(view: View) {
        if(_viewModel.feedDetails.value?.media != null){
            val intent  = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(_viewModel.feedDetails.value?.media?.m))
            startActivity(intent)
        }
    }

}