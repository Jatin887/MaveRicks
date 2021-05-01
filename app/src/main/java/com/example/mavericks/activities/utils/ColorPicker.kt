package com.example.mavericks.activities.utils

object ColorPicker {
    val colors = arrayOf("#b22319",
            "#ef9d29",
            "#fe31b3",
            "#abda23",
            "#138b37",
            "#f0503a",
            "#f4a838",
            "#91f5df",
            "#962329",
            "#91aebb",
            "#c5a2b0",
            "#d42c6e",
            "#cdf230",
            "#0c6f00",
            "#fe1e4d",
            "#f64a7c",
            "#5cdf87"
    )
    var currentColorIndex=0
    fun getColor():String{
        currentColorIndex= (currentColorIndex+1)% colors.size
        return colors[currentColorIndex]
    }

}