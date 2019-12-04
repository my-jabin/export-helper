package com.jiujiu.helper.util

import android.graphics.Color
import kotlin.random.Random

object ColorGenerator {

    fun randomColor(): Int {
        return Color.argb(255, Random.Default.nextInt(256), Random.Default.nextInt(256), Random.Default.nextInt(256))
    }

}