package com.example.sokogarden

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class About : AppCompatActivity() {

//    Declare the variable that will hold the text to speech object
    lateinit var tts : TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        Find the text view and the button by use of their Ids
        val textView = findViewById<TextView>(R.id.aboutTxt)
        val speakButton = findViewById<Button>(R.id.btnListen)

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Try to find a male voice
                val voices = tts.voices
                if (voices != null) {
                    val maleVoice = voices.find { voice ->
                        voice.name.lowercase().contains("male") && 
                        (voice.locale == Locale.UK || voice.locale == Locale.US)
                    } ?: voices.find { it.name.lowercase().contains("male") }

                    if (maleVoice != null) {
                        tts.voice = maleVoice
                    } else {
                        tts.language = Locale.UK
                    }
                } else {
                    tts.language = Locale.UK
                }
            }
        }
//        End

        speakButton.setOnClickListener {
            val text = textView.text.toString()
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }

    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}