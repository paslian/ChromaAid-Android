package com.example.chromaaid.view.ui.Main.screen.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class HomeViewModel : ViewModel() {

    private var spError = MutableLiveData<Boolean>()
    private var spNotFound = MutableLiveData<Boolean>()
    private lateinit var photos: Array<File>
    var spList: MutableLiveData<Array<File>> = MutableLiveData()

    fun initialize(context: Context) {
        spList.value = getPhotos(context)
    }

    private fun getPhotos(context: Context): Array<File> {

        val directory = File(context.externalMediaDirs[0].absolutePath)
        photos = directory.listFiles() as Array<File>
        spList.value = photos

        if (photos.isEmpty()) {
            spNotFound.value = true
            spError.value = false
        } else {
            spError.value = false
            spNotFound.value = false
        }

        return photos
    }

}