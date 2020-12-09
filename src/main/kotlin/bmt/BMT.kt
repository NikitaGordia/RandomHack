package bmt

import RandomGenerator


class BMT(seed: List<Long>, val userId: Int) : RandomGenerator() {

    companion object {

        private const val N = 624
        private const val M = 397
        private const val MATRIX_A = 0x9908B0DF.toInt()
        private const val UPPER_MASK = 0x80000000.toInt()
        private const val LOWER_MASK = 0x7FFFFFFFL.toInt()

        private const val MASK_B = 0x9D2C5680.toInt()
        private const val MASK_C = 0xEFC60000.toInt()
    }

    private val mt = IntArray(N)
    private var mti = 0

    init {
        seed.forEachIndexed { index, l -> mt[index] = l.toInt() }
        update()
    }

    override fun nextRandom(prev: Long): Long {
        if (mti >= N) {
            update()
        }
        var y = mt[mti++]

        y = y xor (y ushr 11)
        y = y xor (y shl 7 and MASK_B)
        y = y xor (y shl 15 and MASK_C)
        y = y xor (y ushr 18)

        return y.toLong() and 0xFFFFFFFFL
    }

    private fun update() {
        for (i in 0 until N) {
            val x = (mt[i] and UPPER_MASK) + (mt[(i + 1) % N] and LOWER_MASK)
            mt[i] = (mt[(i + M) % N] xor (x ushr 1))
            mt[i] = if (x and 1 > 0) mt[i] xor MATRIX_A else mt[i]
        }
        mti = 0
    }
}