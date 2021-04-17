package com.applab.app_luckyindex

import kotlin.math.abs
import kotlin.random.Random

fun getLuckyIndex(n: Int) : Int {
    val r = Random.nextInt(9) + 1
    return (100 - (abs(n - r)) * 10)
}