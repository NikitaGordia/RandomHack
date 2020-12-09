package mt

import Api
import Hacker
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.random.Random

class MTHacket : Hacker<MT> {

    override suspend fun hack(api: Api): MT {
        val user = api.createUser(abs(Random.nextInt()))

        val seq = mutableListOf<Long>()
        val seqSize = 5
        for (i in 1..seqSize)
            seq.add(api.makeBet("Mt", user.id, 1, 1L).realNumber)

        var tm = (user.getDeletionDate().time / 1000 + TimeUnit.HOURS.toSeconds(2)).toInt()
        while (tm > 0) {
            if (tm % 1000 == 0) println("Check $tm")
            MT(tm, user.id).let { mt ->
                val res = Array(seqSize) { mt.nextRandom() }.toList()
                if ((0 until seqSize).all {
                    res[it] == seq[it]
                }) {
                    println("DIFF -> ${(user.getDeletionDate().time / 1000 + TimeUnit.HOURS.toSeconds(2)).toInt() - tm}")
                    println("Seed for MT: $tm")
                    return mt
                }
            }
            tm--
        }

        return MT(0, 0)
    }
}