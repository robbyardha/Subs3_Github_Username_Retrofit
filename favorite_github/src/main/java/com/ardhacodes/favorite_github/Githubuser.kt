package com.ardhacodes.favorite_github

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Githubuser(
    val login : String,
    val id : Int,
    val avatar_url : String,
    val type : String,
):Parcelable