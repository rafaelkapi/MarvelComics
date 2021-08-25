package com.cactus.marvelcomics.features

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.cactus.marvelcomics.R
import com.cactus.marvelcomics.common.base.BaseActivity
import com.cactus.marvelcomics.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }




}