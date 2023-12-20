package com.example.chromaaid.view.ui.Detect

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.chromaaid.R
import com.example.chromaaid.databinding.ActivityDetectImgGalleryBinding
import com.example.chromaaid.ml.Model1
import com.example.chromaaid.view.ui.Detect.manual.handler.ColorDetectHandler
import com.example.chromaaid.view.ui.Main.MainActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder

class DetectImgGalleryActivity : AppCompatActivity() {
    fun onBackClick(view: View?) {
        finish()
    }

    private val detectHandler = ColorDetectHandler()

    private lateinit var bindind: ActivityDetectImgGalleryBinding

    private var index: Int = 0
    private lateinit var uri: Uri
    private lateinit var fileName: String

    private lateinit var photo: ImageView
    private lateinit var deleteBtn: Button
    private lateinit var detectBtn:Button

    private lateinit var fpColorName: TextView
    private lateinit var fpDominantColor: TextView
    private lateinit var fpColorHex: TextView
    private lateinit var fpPointer: LottieAnimationView
    private lateinit var fpColor: View
    private lateinit var fpCursor: TextView


    private lateinit var bitmap: Bitmap

    private val detectImgViewModel = DetectImgViewModel()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        bindind = ActivityDetectImgGalleryBinding.inflate(layoutInflater)
        setContentView(bindind.root)

        setInit()

        fpColorHex.setOnClickListener {
            copyText(fpColorHex.text.toString())
        }

        fpColorName.setOnClickListener {
            copyText(fpColorName.text.toString())
        }

        photo.setOnTouchListener { _, motionEvent ->

            setCardinates(motionEvent)
            detect(motionEvent)
            true
        }

        val labelFile = "label.txt"
        val labels =
            application.assets.open(labelFile).bufferedReader().use { it.readText() }.split("\n")


        detectBtn.setOnClickListener {
            val model = Model1.newInstance(this)
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(getImageData())

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val max = getMax(outputFeature0.floatArray)

            fpDominantColor.text = getString(R.string.dominant_color, labels[max])
            model.close()
            val message: String = getString(R.string.dominant_color, labels[max])
            showCustomResultDialogBox(message)
        }

        deleteBtn.setOnClickListener {
            fileName = getPhotoList()[index].name
            val message: String = getString(R.string.confirm_delete)
            showCustomDeleteDialogBox(message)
        }
    }

    private fun showCustomResultDialogBox(message: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_alert)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)

        btnYes.text = getString(R.string.ok)
        btnNo.visibility = View.GONE
        tvMessage.text = message

        btnYes.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showCustomDeleteDialogBox(message: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_alert)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)

        tvMessage.text = message

        btnYes.setOnClickListener {
            delete(fileName)
            intentToHome()
            Toast.makeText(applicationContext,getString(R.string.success_delete), Toast.LENGTH_LONG).show()
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun getImageData() : ByteBuffer {
        val num = 224
        val resized : Bitmap = Bitmap.createScaledBitmap(bitmap, num, num, true)
        val imgData: ByteBuffer = ByteBuffer.allocateDirect(Float.SIZE_BYTES * num * num * 3)
        imgData.order(ByteOrder.nativeOrder())

        val intValues = IntArray(num * num)
        resized.getPixels(intValues, 0, resized.width, 0, 0, resized.width, resized.height)

        var pixel = 0
        for (i in 0 until num) {
            for (j in 0 until num) {
                val `val` = intValues[pixel++]
                imgData.putFloat((`val` shr 16 and 0xFF) / 255f)
                imgData.putFloat((`val` shr 8 and 0xFF) / 255f)
                imgData.putFloat((`val` and 0xFF) / 255f)
            }
        }
        return imgData
    }

    private fun copyText(text: String) {
        val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("copy_text", text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(applicationContext, getString(R.string.copied_text, text), Toast.LENGTH_SHORT).show()
    }

    private fun setCardinates(motionEvent: MotionEvent){

        fpCursor.y = motionEvent.y
        fpCursor.x = motionEvent.x -150

        fpPointer.y = fpCursor.y - 300
        fpPointer.x = fpCursor.x + fpCursor.x / 2

        if (fpPointer.x >= photo.right - 50f){
            fpPointer.x = photo.right - 50f
        }

        if (fpPointer.y >= photo.bottom - 50f){
            fpPointer.y = photo.bottom - 50f
        }

        if (fpPointer.x <= photo.left - 20f){
            fpPointer.x = photo.left - 20f
        }

        if (fpPointer.y <= photo.top - 20f){
            fpPointer.y = photo.top -20f
        }
    }

    @SuppressLint("SetTextI18n")
    private fun detect(motionEvent: MotionEvent) {

        if (motionEvent.action == MotionEvent.ACTION_MOVE || motionEvent.action == MotionEvent.ACTION_DOWN) {

            val currColor = detectHandler.detect(photo, fpPointer)

            val name = currColor.name
            val hex = currColor.hex
            val r = currColor.r
            val g = currColor.g
            val b = currColor.b

            fpColorHex.text = "#$hex"
            fpColorName.text = name

            fpColor.setBackgroundColor(Color.rgb(r!!, g!!, b!!))

        }
    }

    private fun getMax(arr: FloatArray): Int {
        var ind = 0
        var min = 0.0f

        for (i in 0..14) {
            if (arr[i] > min) {
                Log.d("DetectImgGalleryActivity", "Position =$i")
                min = arr[i]
                ind = i
            }
        }
        return ind
    }

    private fun setInit() {

        // Views
        photo = bindind.specificPhotoImageView
        detectBtn = bindind.detectBtn
        deleteBtn = bindind.deleteBtn
        fpColorName = bindind.fpColorName
        fpDominantColor = bindind.fpDominantColor
        fpPointer = bindind.fpPointer
        fpColorHex = bindind.fpColorHex
        fpColor = bindind.fpColor
        fpCursor = bindind.fpCursor

        // Value
        index = intent.getIntExtra("photoIndex", 0)
        val uriString = intent.getStringExtra("uri")


        if (uriString != null){
            uri = Uri.parse(uriString)
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            Glide.with(this).load(uri).into(photo)
            deleteBtn.visibility = View.INVISIBLE
        }else{
            val photoList = getPhotoList()
            val firstPhotoUri = Uri.fromFile(photoList[index])
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, firstPhotoUri)
            Glide.with(this).load(getPhotoList()[index]).into(photo)
        }
    }

    private fun intentToHome(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun delete(fileName: String) {

        val file = File(externalMediaDirs[0], fileName)

        if (file.exists()) {
            file.delete()
        }
    }

    private fun getPhotoList(): Array<File> {

        return detectImgViewModel.getPhotos(this)

    }
}