package Server

import Model.Comment
import Model.Game
import Model.Review
import java.time.Year
import java.util.*
import kotlin.collections.HashMap

class ReviewingServices {
    private val games = mutableListOf<Game>()
    private val nameToGame = HashMap<String, Game>()

    fun viewReviews (gameName: String) : String = Arrays.toString(nameToGame[gameName]?.getReviews()?.toTypedArray())
    fun viewComments (gameName: String) : String = Arrays.toString(nameToGame[gameName]?.getComments()?.toTypedArray())

    fun reviewGame (gameName: String, rating: Float, userId: String) : String? {
        val game = nameToGame[gameName]
        return if (game != null && !game.getReviews().contains(Review(userId, 0f)))  {
            game.getReviews().add(Review(userId, rating))
            return "game reviewed"
        } else null
    }

    fun commentGame (gameName: String, message : String, userId: String) : String? {
        val game = nameToGame[gameName]
        return if (game != null && !game.getComments().contains(Comment(userId, "nic"))) {
            game.getComments().add(Comment(userId, message.substring(ordinalIndexOf(message, " ", 2))))
            "Game commented"
        } else null
    }

    fun addGame(name: String, releaseYear:Year) : String {
        val game = Game(name, releaseYear)
        games.add(game);
        nameToGame[name] = game;
        return "game added"
    }
}

fun ordinalIndexOf(str: String, substr: String?, n: Int): Int {
    var n = n
    var pos = str.indexOf(substr!!)
    while (--n > 0 && pos != -1) pos = str.indexOf(substr, pos + 1)
    return pos
}