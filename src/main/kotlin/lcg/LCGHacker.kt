package lcg

import Api
import Hacker
import java.lang.Exception
import java.math.BigInteger
import kotlin.math.abs
import kotlin.random.Random

class LCGHacker : Hacker<LCG> {

    override suspend fun hack(api: Api): LCG {
        val user = api.createUser(abs(Random.nextInt()))

        val seq = mutableListOf<Int>()
        val seqSize = 30
        for (i in 1..seqSize)
            seq.add(api.makeBet("Lcg", user.id, 1, seq.lastOrNull()?.toLong() ?: 1L).realNumber.toInt())

        val bigSeq = seq.map { BigInteger.valueOf(it.toLong()) }

        val testSize = 10
        val train = bigSeq.slice(0 until (seqSize - testSize))
        val test = bigSeq.slice((seqSize - testSize) until seqSize)

        val m = getM(train)
        val a = getA(train[0], train[1], train[2], m)
        val c = getC(train[0], train[1], a, m)

        val lcg = LCG(user.id, test.last().toInt().toLong(), a = a, c = c, m = m)
        val res = (0 until testSize).map {
            lcg.nextRandom((test.getOrNull(it - 1)?.toInt() ?: train.last().toInt()).toLong())
        }
        return lcg.takeIf {
            (0 until testSize).all {
                test[it].toInt() == res[it].toInt()
            }.also {
                if (it) println("LCG params found: a=$a, c=$c, m=$m")
            }
        } ?: throw Exception("Can not find params! Try again")
    }

    private fun getM(seq: List<BigInteger>): BigInteger {
        val diff = mutableListOf<BigInteger>()
        for (i in 0 until seq.size - 1)
            diff.add(seq[i + 1] - seq[i])

        val z = mutableListOf<BigInteger>()
        for (i in 0 until diff.size - 2)
            z.add((diff[i] * diff[i + 2] - diff[i + 1] * diff[i + 1]).abs())

        return z.fold(z.first(), { acc, it -> acc.gcd(it) })
    }

    private fun getA(seq0: BigInteger, seq1: BigInteger, seq2: BigInteger, mod: BigInteger) =
        ((seq2 - seq1) * (seq1 - seq0).modInverse(mod)).mod(mod)

    private fun getC(seq0: BigInteger, seq1: BigInteger, a: BigInteger, m: BigInteger) =
        (seq1 - seq0 * a).mod(m)
}

/**/