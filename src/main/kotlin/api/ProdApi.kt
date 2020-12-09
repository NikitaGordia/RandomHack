package api

import Api
import BetResult
import User
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL

class ProdApi : Api {

    override suspend fun createUser(id: Int): User {
        return req("/createacc?id=$id", User::class.java)
    }

    override suspend fun makeBet(mode: String, playerId: Int, bet: Int, number: Long): BetResult {
        return req("/play$mode?id=$playerId&bet=$bet&number=$number", BetResult::class.java)
    }

    private fun <T> req(str: String, type: Class<T>): T {
        val url = URL("http://95.217.177.249/casino$str")

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            return inputStream.bufferedReader().use {
                Gson().fromJson(it.readLines().joinToString(), type)
            }
        }
    }
}