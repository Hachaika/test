package com.eltex.androidschool.adapter

class EventPayload(
    val likedByMe: Boolean? = null,
    val participatedByMe: Boolean? = null,
) {
    fun isNotEmpty(): Boolean = likedByMe != null || participatedByMe != null
}