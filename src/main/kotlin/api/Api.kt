import lcg.LCG
import java.lang.Exception
import java.math.BigInteger

interface Api {

    suspend fun createUser(id: Int): User

    suspend fun makeBet(mode: String, playerId: Int, bet: Int, number: Int): BetResult
}

class LocalApi : Api {

    private var prevNumber = -1

    private val map = mutableMapOf<Int, User>()

    override suspend fun createUser(id: Int): User =
        User(id, 1000, "some time :)").also {
            map[id] = it
        }

    override suspend fun makeBet(mode: String, playerId: Int, bet: Int, number: Int): BetResult {
        val user = map[playerId] ?: throw Exception("Create user!")
        if (user.money < bet) throw Exception("No money!")
        val num = when (mode) {
//            else -> LCG().next(prevNumber.takeIf { it != -1 })
            else -> prevNumber
        }
        prevNumber = num
        val newUser = if (number == num) user.copy(money = user.money + bet) else user.copy(money = user.money - bet)
        map[user.id] = newUser

        return if (number == num) BetResult("Win", newUser, num)
        else BetResult("Lose",  newUser, num)
    }
}

data class User(
    val id: Int,
    val money: Int,
    val deletionTime: String
)

data class BetResult(
    val message: String,
    val account: User,
    val realNumber: Int
)