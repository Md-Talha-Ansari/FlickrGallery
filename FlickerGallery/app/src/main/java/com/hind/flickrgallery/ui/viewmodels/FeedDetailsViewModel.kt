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
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

class FeedDetailsViewModel : ViewModel() {
    private  val TAG = "FeedDetailsViewModel"
    private val _feedDetails = MutableLiveData<Feed>()
    val feedDetails:LiveData<Feed> = _feedDetails

    private val _fileWriteStatus = MutableLiveData<Int>()
    public val fileWriteStatus:LiveData<Int> = _fileWriteStatus

    fun setFeedDetails(feed:Feed){
        _feedDetails.value = feed
    }

    suspend fun saveImage(bitmap: Bitmap,contentResolver:ContentResolver?) = withContext(Dispatchers.IO){
        val fileUri = Uri.parse(feedDetails.value?.media?.m)
        var fileName:String = "${System.currentTimeMillis()}.jpg"
        fileUri?.lastPathSegment?.also { fileName = it }
        var outputStream: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                if(imageUri != null) {
                    try {
                        outputStream = resolver.openOutputStream(imageUri)
                    }catch (ex:FileNotFoundException) {
                        Log.d(TAG,ex.message.toString())
                        _fileWriteStatus.value = UIConstants.ERROR_CANNOT_SAVE_IMAGE
                    }
                }

            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, fileName)
            try {
                outputStream = FileOutputStream(image)
            }catch (ex:FileNotFoundException) {
                Log.d(TAG,ex.message.toString())
                _fileWriteStatus.value = UIConstants.ERROR_CANNOT_SAVE_IMAGE
            }
        }
        if(outputStream != null){
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
            _fileWriteStatus.value = UIConstants.STATE_IDEAL
        }
    }
}