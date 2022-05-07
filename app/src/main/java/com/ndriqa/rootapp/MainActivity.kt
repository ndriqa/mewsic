package com.ndriqa.rootapp

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.ndriqa.rootapp.base.BaseActivity
import com.ndriqa.rootapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initBaseFunctions()
    }

    override fun onLoad() {}

    override fun setToolbar() {
        binding.title.text = "Testione"
    }

    override fun onClickListeners() {
        binding.menuButton.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }
    }

    override fun observers() {}
}