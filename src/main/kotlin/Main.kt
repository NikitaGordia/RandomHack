import api.ProdApi
import kotlinx.coroutines.runBlocking
import kotlin.math.max

fun main() {

    val api: Api = ProdApi()

    runBlocking {
//        val lcgGenerator = LCGHacker().hack(api)
//        hack(api, lcgGenerator)
    }
}

suspend fun hack(api: Api, generator: RandomGenerator) {
    var user = User(0, 0, "")
    var lastRand = generator.lastRandom
    while (user.money < 1000000) {
        lastRand = generator.nextRandom(lastRand)
        println("Next bet -> $lastRand, cash -> ${user.money}")
        val resp = api.makeBet("Lcg", generator.userId, max(user.money, 970), lastRand.toInt())
        user = resp.account
    }
    println("BOOOM! You are millioner! $$$$$$$$$$$$$$$")
}