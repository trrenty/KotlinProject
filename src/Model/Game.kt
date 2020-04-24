package Model

import java.time.Year

class Game(private val title : String, private val releaseYear: Year) {
    private val reviews = mutableListOf<Review>()
    private val comments = mutableListOf<Comment>()

    public fun getReviews() : MutableList<Review> {
        return reviews
    }

    public fun getComments() : MutableList<Comment> {
        return comments
    }
}