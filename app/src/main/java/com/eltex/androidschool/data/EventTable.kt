package com.eltex.androidschool.data

object EventTable {
    const val TABLE_NAME = "events"
    const val ID = "id"
    const val CONTENT = "content"
    const val DATE = "date"
    const val LIKED_BY_ME = "likedByMe"
    const val AUTHOR = "author"
    const val STATUS = "status"
    const val STATUS_TIME = "statusTime"
    const val LINK = "link"
    const val PARTICIPANTS = "participants"
    const val PARTICIPATED_BY_ME = "participatedByMe"

    val allColumns = arrayOf(ID, CONTENT, DATE, LIKED_BY_ME, AUTHOR, STATUS, STATUS_TIME, LINK, PARTICIPANTS, PARTICIPATED_BY_ME)
}
