package com.ndriqa.rootapp

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build.*
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.ndriqa.rootapp.base.BaseActivity
import com.ndriqa.rootapp.databinding.ActivityMainBinding
import com.ndriqa.rootapp.utility.data.uri
import com.ndriqa.rootapp.viewmodels.MainActivityViewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: MainActivityViewModel by viewModels()
    val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (VERSION.SDK_INT >= VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initBaseFunctions()
    }

    override fun onLoad() {
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }

    override fun setToolbar() {
        binding.toolbar.title.text = getString(R.string.app_name)
    }

    override fun onClickListeners() {
        binding.toolbar.menuButton.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }
    }

    override fun observers() {
        viewModel.nowPlaying.observe(this) {
            it?.let {
                with(mediaPlayer){
                    stop()
                    reset()
                    setDataSource(applicationContext, it.uri())
                    prepare()
                    start()
                }
            }
        }
    }
}