package com.example.chromaaid.view.ui.Detect.manual.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chromaaid.view.ui.Detect.manual.util.ColorUtils

class ColorDetectViewModel : ViewModel() {

    private lateinit var colorNotFound : MutableLiveData<Boolean>
    private lateinit var detectError : MutableLiveData<Boolean>
    private lateinit var colorList : MutableLiveData<ArrayList<ColorModel>>
    private lateinit var colors : ArrayList<ColorModel>


    fun initData(){

        colorNotFound = MutableLiveData()
        detectError = MutableLiveData()
        colorList = MutableLiveData()

        colors = ColorUtils().getColorList()
        colorList.value = colors
    }

    fun getColorNameFromRgb(r: Int, g: Int, b: Int) : String?{
        val colorList: ArrayList<ColorModel> = colors
        var closestMatch: ColorModel? = null
        var minMSE = Int.MAX_VALUE
        var mse: Int
        for (c: ColorModel in colorList) {
            mse = c.computeMSE(r, g, b)
            if (mse < minMSE) {
                minMSE = mse
                closestMatch = c
            }
        }
        return if (closestMatch != null) {
            colorNotFound.value = false
            detectError.value = false
            closestMatch.name
        } else {
            colorNotFound.value = true
            ""
        }
    }

}