package Model

import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class Review(private val userName : String, private val rating : Float, private val date : LocalDate = LocalDate.now()) {
    override fun toString(): String {
        return "Review(userName='$userName', rating=$rating, date=$date)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Review

        if (userName != other.userName) return false

        return true
    }

    override fun hashCode(): Int {
        return userName.hashCode()
    }

}