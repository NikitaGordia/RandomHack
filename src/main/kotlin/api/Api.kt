import java.text.SimpleDateFormat
import java.util.*

interface Api {

    suspend fun createUser(id: Int): User

    suspend fun makeBet(mode: String, playerId: Int, bet: Int, number: Long): BetResult
}

class LocalApi : Api {

    private var prevNumber = -1

    private val map = mutableMapOf<Int, User>()

    override suspend fun createUser(id: Int): User =
        User(id, 1000, "some time :)").also {
            map[id] = it
        }

    override suspend fun makeBet(mode: String, playerId: Int, bet: Int, number: Long): BetResult {
//        val user = map[playerId] ?: throw Exception("Create user!")
//        if (user.money < bet) throw Exception("No money!")
//        val num = when (mode) {
////            else -> LCG().next(prevNumber.takeIf { it != -1 })
//            else -> prevNumber
//        }
//        prevNumber = num
//        val newUser = if (number == num) user.copy(money = user.money + bet) else user.copy(money = user.money - bet)
//        map[user.id] = newUser
//
//        return if (number == num) BetResult("Win", newUser, num)
//        else BetResult("Lose",  newUser, num)
        return TODO()
    }
}

data class User(
    val id: Int,
    val money: Int,
    val deletionTime: String
) {

    fun getDeletionDate(): Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(deletionTime)
}

data class BetResult(
    val message: String,
    val account: User,
    val realNumber: Long
)