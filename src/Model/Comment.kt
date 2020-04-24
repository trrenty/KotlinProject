package Model

import java.time.LocalDate

class Comment(private val userName : String, private val comment : String, private val date : LocalDate = LocalDate.now()) {
    override fun toString(): String {
        return "Comment(userName='$userName', comment='$comment', date=$date)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comment

        if (userName != other.userName) return false

        return true
    }

    override fun hashCode(): Int {
        return userName.hashCode()
    }


}