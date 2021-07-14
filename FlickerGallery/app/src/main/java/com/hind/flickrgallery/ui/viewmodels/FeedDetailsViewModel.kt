package com.hind.flickrgallery.ui.viewmodels

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hind.flickrgallery.businesslogic.models.response.publicfeeds.Feed
import com.hind.flickrgallery.ui.screens.UIConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

class FeedDetailsViewModel : ViewModel() {
    private val TAG = "FeedDetailsViewModel"
    private val _feedDetails = MutableLiveData<Feed>()
    val feedDetails: LiveData<Feed> = _feedDetails

    private val _fileWriteStatus = MutableLiveData<Int>()
    val fileWriteStatus: LiveData<Int> = _fileWriteStatus

    fun setFeedDetails(feed: Feed) {
        _feedDetails.value = feed
    }

    /**
     * Method to save image to external storage.
     * @param bitmap Bitmap to save.
     * @contentResolver ContentResolver for Android Q and above to use MediaStore.
     */
    suspend fun saveImage(bitmap: Bitmap, contentResolver: ContentResolver?) =
        withContext(Dispatchers.IO) {
            launch(Dispatchers.Main) {
                //Update file write status
                _fileWriteStatus.value = UIConstants.STATE_IDEAL//Update file write status
            }
            //Get the file uri
            val fileUri = Uri.parse(feedDetails.value?.media?.m)
            //If file uri cannot be parsed use current time for file name
            var fileName: String = "${System.currentTimeMillis()}.jpg"
            //Set file name from uri
            fileUri?.lastPathSegment?.also { fileName = it }
            //Create output stream
            var outputStream: OutputStream? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //For Android Q and above
                contentResolver?.also { resolver ->
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }
                    val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    if (imageUri != null) {
                        try {
                            outputStream = resolver.openOutputStream(imageUri)
                        } catch (ex: FileNotFoundException) {
                            Log.d(TAG, ex.message.toString())
                            //Update file write status
                            launch(Dispatchers.Main) {
                                _fileWriteStatus.value = UIConstants.ERROR_CANNOT_SAVE_IMAGE
                            }
                        }
                    }

                }
            } else {//For below Android Q
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val image = File(imagesDir, fileName)
                try {
                    outputStream = FileOutputStream(image)
                } catch (ex: FileNotFoundException) {
                    Log.d(TAG, ex.message.toString())
                    launch(Dispatchers.Main) {
                        //Update file write status
                        _fileWriteStatus.value = UIConstants.ERROR_CANNOT_SAVE_IMAGE
                    }
                }
            }
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                launch(Dispatchers.Main) {
                    //Update file write status
                    _fileWriteStatus.value = UIConstants.FILE_SAVED
                }
            }
        }
}