package com.ardhacodes.github_retro

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemindedData(
    var ThisisRemainded: Boolean = false
):Parcelable