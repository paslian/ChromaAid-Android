package com.example.chromaaid.view.ui.Detect.manual.model

class ColorModel(colorName: String, R: Int, G: Int, B: Int) {


    constructor(colorName: String, R: Int, G: Int, B: Int,hex : String) : this(colorName,R,G,B){
        this.hex = hex
    }


    var hex : String? = null
    var r : Int? = null
    var g : Int? = null
    var b : Int? = null

    var name : String? = null


    init {


        r = R
        g = G
        b = B

        name = colorName

    }


    fun computeMSE(pixR: Int, pixG: Int, pixB: Int): Int {
        return (((pixR - r!!) * (pixR - r!!) + (pixG - g!!) * (pixG - g!!) + ((pixB - b!!)
                * (pixB - b!!))) / 3)
    }
}