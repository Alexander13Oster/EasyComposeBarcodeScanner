package de.alexander13oster.easycomposebarcodescanner

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class TextAnalyzer(private val context: Context) : ImageAnalysis.Analyzer {

    private val options = TextRecognizerOptions.DEFAULT_OPTIONS

    private val scanner = TextRecognition.getClient(options)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image
            ?.let { image ->
                scanner.process(
                    InputImage.fromMediaImage(
                        image, imageProxy.imageInfo.rotationDegrees
                    )
                ).addOnSuccessListener { results ->
                    results.textBlocks
                        .takeIf { it.isNotEmpty() }
                        ?.joinToString(",") { it.text }
                        ?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                }.addOnCompleteListener {
                    imageProxy.close()
                }
            }
    }
}