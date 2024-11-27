package com.eltex.androidschool.utils

import android.database.Cursor

fun Cursor.getLongOrThrow(columnName: String): Long {
    val index = getColumnIndexOrThrow(columnName)
    return getLong(index)
}

fun Cursor.getStringOrThrow(columnName: String): String {
    val index = getColumnIndexOrThrow(columnName)
    return getString(index) ?: throw IllegalArgumentException("Column $columnName is null")
}

fun Cursor.getIntOrThrow(columnName: String): Int {
    val index = getColumnIndexOrThrow(columnName)
    return getInt(index)
}

fun Cursor.getBooleanOrThrow(columnName: String): Boolean {
    val index = getColumnIndexOrThrow(columnName)
    return getInt(index) != 0
}