package mt

import RandomGenerator


class MT(seed: Int, val userId: Int) : RandomGenerator() {

    companion object {
        private const val UPPER_MASK = -0x80000000
        private const val LOWER_MASK = 0x7fffffff
        private const val N = 624
        private const val M = 397
        private val MAGIC = intArrayOf(0x0, -0x66f74f21)
        private const val MAGIC_FACTOR1 = 1812433253
        private const val MAGIC_MASK1 = -0x62d3a980
        private const val MAGIC_MASK2 = -0x103a0000
    }

    private val mt: IntArray = IntArray(N)
    private var mti = 0

    init {
        mt[0] = seed
        mti = 1
        while (mti < N) {
            mt[mti] = MAGIC_FACTOR1 * (mt[mti - 1] xor (mt[mti - 1] ushr 30)) + mti
            mti++
        }
    }

    override fun nextRandom(prev: Long): Long = next().let {
        var x = it.toLong()
        if (x < 0) x += 2L * Int.MAX_VALUE + 2
        x
    }

    private fun next(): Int {
        var y: Int
        var kk: Int
        if (mti >= N) {
            kk = 0
            while (kk < N - M) {
                y = mt[kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor (y ushr 1) xor MAGIC[y and 0x1]
                kk++
            }
            while (kk < N - 1) {
                y = mt[kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor (y ushr 1) xor MAGIC[y and 0x1]
                kk++
            }
            y = mt[N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor (y ushr 1) xor MAGIC[y and 0x1]
            mti = 0
        }
        y = mt[mti++]

        y = y xor (y ushr 11)
        y = y xor (y shl 7 and MAGIC_MASK1)
        y = y xor (y shl 15 and MAGIC_MASK2)
        y = y xor (y ushr 18)
        return (y ushr 32 - 32)
    }
}