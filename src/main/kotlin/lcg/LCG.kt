package lcg

import RandomGenerator
import java.math.BigInteger

class LCG(
    userId: Int,
    lastRandom: BigInteger,
    val a: BigInteger,
    val c: BigInteger,
    val m: BigInteger
) : RandomGenerator(userId, lastRandom) {

    override fun nextRandom(prev: BigInteger) = (prev * a + c).mod(m)
}