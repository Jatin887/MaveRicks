package com.example.mavericks.activities.utils

import com.example.mavericks.R

object IconPicker {
    val icons = arrayOf(R.drawable.icon1,
            R.drawable.icon2,
            R.drawable.icon3,
            R.drawable.icon4,
            R.drawable.icon5,
            R.drawable.icon6,
            R.drawable.icon7,
            R.drawable.icon8,
            R.drawable.icon9,
            R.drawable.calculator_icon
    )
    var currentIconIndex=0
    fun getIcon(): Int {
        currentIconIndex= (currentIconIndex+1)% icons.size
        return icons[currentIconIndex]
    }

}