package com.ndriqa.rootapp

import android.os.Build.*
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.ndriqa.rootapp.base.BaseActivity
import com.ndriqa.rootapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (VERSION.SDK_INT >= VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initBaseFunctions()
    }

    override fun onLoad() {

    }

    override fun setToolbar() {
        binding.toolbar.title.text = "Testione"
    }

    override fun onClickListeners() {
        binding.toolbar.menuButton.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }
    }

    override fun observers() {}
}