package lcg

import RandomGenerator
import java.math.BigInteger

class LCG(
    val userId: Int,
    val lastRandom: Long,
    val a: BigInteger,
    val c: BigInteger,
    val m: BigInteger
) : RandomGenerator() {

    override fun nextRandom(prev: Long): Long = (BigInteger.valueOf(prev) * a + c).mod(m).toInt().toLong()
}