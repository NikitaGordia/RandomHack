package utils

import kotlin.random.Random

fun randomInt32() = Random.nextInt() % (1 shl 31).inv()