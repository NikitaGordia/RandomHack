import api.ProdApi
import bmt.BMT
import bmt.BMTHacker
import kotlinx.coroutines.runBlocking
import lcg.LCG
import lcg.LCGHacker
import mt.MT
import mt.MTHacket
import kotlin.math.max

fun main() {

    val api: Api = ProdApi()

    runBlocking {
//        hackViaMT(api)
//        hackViaLCG(api)
        hackViaBMT(api)
    }
}

suspend fun hackViaLCG(api: Api) {
    val lcgGenerator = LCGHacker().hack(api)
    hack(api, lcgGenerator)
}

suspend fun hackViaMT(api: Api) {
    val mtGenerator = MTHacket().hack(api)
    hack(api, mtGenerator)
}

suspend fun hackViaBMT(api: Api) {
    val mtGenerator = BMTHacker().hack(api)
    hack(api, mtGenerator)
}

suspend fun hack(api: Api, generator: BMT) {
    var user = User(0, 0, "")
    while (user.money < 1000000) {
        val betNum = generator.nextRandom()
        println("Next bet -> $betNum, cash -> ${user.money}")
        val resp = api.makeBet("BetterMt", generator.userId, max(user.money, 300), betNum)
        user = resp.account
    }
    println("BOOOM! You are millioner via MT! $$$$$$$$$$$$$$$")
}

suspend fun hack(api: Api, generator: MT) {
    var user = User(0, 0, "")
    while (user.money < 1000000) {
        val betNum = generator.nextRandom()
        println("Next bet -> $betNum, cash -> ${user.money}")
        val resp = api.makeBet("Mt", generator.userId, max(user.money, 900), betNum)
        user = resp.account
    }
    println("BOOOM! You are millioner via MT! $$$$$$$$$$$$$$$")
}

suspend fun hack(api: Api, generator: LCG) {
    var user = User(0, 0, "")
    var lastRand = generator.lastRandom
    while (user.money < 1000000) {
        lastRand = generator.nextRandom(lastRand)
        println("Next bet -> $lastRand, cash -> ${user.money}")
        val resp = api.makeBet("Lcg", generator.userId, max(user.money, 900), lastRand.toInt().toLong())
        user = resp.account
    }
    println("BOOOM! You are millioner via LCG! $$$$$$$$$$$$$$$")
}