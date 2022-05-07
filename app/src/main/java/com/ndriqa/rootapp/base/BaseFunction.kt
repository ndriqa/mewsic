package com.ndriqa.rootapp.base

interface BaseFunction {
    fun onLoad()
    fun setToolbar()
    fun onClickListeners()
    fun observers()

    fun initBaseFunctions(){
        onLoad()
        setToolbar()
        onClickListeners()
        observers()
    }
}
