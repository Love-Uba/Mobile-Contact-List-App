package com.example.weeksix

import android.graphics.Color
import com.google.firebase.database.Exclude

/**
 *data class holding database table entities to be used throughout the project
 * */

data class ItemEntity(
    @get: Exclude
    var id: String? = null,

    val fullName: String? = null,
    val phone: String? = null,
    var email: String? = null,
    var color: Int = Color.argb(255, 156, 39, 176)
)