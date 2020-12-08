import java.math.BigInteger

interface Hacker<T : RandomGenerator> {

    suspend fun hack(api: Api): T
}

abstract class RandomGenerator(val userId: Int, val lastRandom: BigInteger) {

    abstract fun nextRandom(prev: BigInteger = BigInteger.ZERO): BigInteger
}