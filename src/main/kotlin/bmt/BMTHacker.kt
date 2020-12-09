package bmt

import Api
import Hacker
import kotlin.math.abs
import kotlin.math.floor
import kotlin.random.Random


class BMTHacker : Hacker<BMT> {

    override suspend fun hack(api: Api): BMT {
        val user = api.createUser(abs(Random.nextInt()))

        val seq = mutableListOf<Long>()
        val seqSize = 624
        for (i in 1..seqSize)
            seq.add(api.makeBet("BetterMt", user.id, 1, 1L).realNumber)

        return BMT(seq.map {
            untemp(it)
        }, user.id)
    }

    private fun untemp(y: Long): Long {
        var x = undoRight(y, 18)
        x = undoLeft(x, 15, 0xefc60000)
        x = undoLeft(x, 7, 0x9d2c5680)
        x = undoRight(x, 11)
        return x
    }

    private fun undoRight(y: Long, shift: Int): Long {
        var x = y.toInt()
        var i = shift
        while (i < 32) {
            x = x xor (y.toInt() ushr i)
            i += shift
        }
        return x.toLong()
    }

    private fun undoLeft(y: Long, shift: Int, mask: Long): Long {
        var x = y
        var window = ((1 shl shift) - 1).toLong()
        var i = 0
        while (i < floor((32 / shift).toDouble())) {
            x = x xor (((window and x) shl shift) and mask)
            window = window shl shift
            i++
        }
        return x
    }
}
