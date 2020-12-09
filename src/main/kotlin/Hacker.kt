import java.math.BigInteger

interface Hacker<T : RandomGenerator> {

    suspend fun hack(api: Api): T
}

abstract class RandomGenerator() {

    abstract fun nextRandom(prev: Long = 0): Long
}