package com.iogando.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var timerText: TextView
    private lateinit var btnStartStop: Button
    private lateinit var btnTimer5: Button
    private lateinit var btnTimer10: Button
    private lateinit var btnSpotify: Button
    private lateinit var btnImg1: Button
    private lateinit var btnImg2: Button
    private lateinit var btnImg3: Button
    private lateinit var btnImg4: Button

    private var countDownTimer: CountDownTimer? = null
    private var isRunning = false
    private var currentImg = 0
    private val images = listOf(
        R.drawable.fundo1,
        R.drawable.fundo2,
        R.drawable.fundo3,
        R.drawable.fundo4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        timerText = findViewById(R.id.timerText)
        btnStartStop = findViewById(R.id.btnStartStop)
        btnTimer5 = findViewById(R.id.btnTimer5)
        btnTimer10 = findViewById(R.id.btnTimer10)
        btnSpotify = findViewById(R.id.btnSpotify)
        btnImg1 = findViewById(R.id.btnImg1)
        btnImg2 = findViewById(R.id.btnImg2)
        btnImg3 = findViewById(R.id.btnImg3)
        btnImg4 = findViewById(R.id.btnImg4)

        imageView.setImageResource(images[currentImg])

        btnImg1.setOnClickListener { switchImage(0) }
        btnImg2.setOnClickListener { switchImage(1) }
        btnImg3.setOnClickListener { switchImage(2) }
        btnImg4.setOnClickListener { switchImage(3) }

        btnStartStop.setOnClickListener {
            if (isRunning) stopTimer()
            else startTimer(Long.MAX_VALUE) // livre
        }

        btnTimer5.setOnClickListener {
            startTimer(5 * 60 * 1000)
        }

        btnTimer10.setOnClickListener {
            startTimer(10 * 60 * 1000)
        }

        btnSpotify.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("spotify:"))
            if (intent.resolveActivity(packageManager) != null) startActivity(intent)
            else startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/")))
        }
    }

    private fun switchImage(index: Int) {
        currentImg = index
        imageView.setImageResource(images[currentImg])
    }

    private fun startTimer(duration: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val min = (millisUntilFinished / 1000) / 60
                val sec = (millisUntilFinished / 1000) % 60
                timerText.text = String.format("%02d:%02d", min, sec)
            }
            override fun onFinish() {
                timerText.text = "00:00"
                isRunning = false
                btnStartStop.text = "Iniciar"
            }
        }.start()
        isRunning = true
        btnStartStop.text = "Parar"
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
        isRunning = false
        btnStartStop.text = "Iniciar"
    }
}
