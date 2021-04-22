package com.ardhacodes.favorite_github

import android.net.Uri
import android.provider.BaseColumns

object BaseColumnDB {
    const val PACKAGE_NAME = "com.ardhacodes.github_retro"
    const val SCHEME = "content"

    internal class FavoriteUserColumn: BaseColumns {
        companion object {

            const val TABLE_NAME = "table_favoriteuser"
            const val ID = "id"
            const val USERNAME = "login"
            const val AVATAR_URL = "avatar_url"
            const val TYPE = "type"

            val CONTENT_URI = Uri.Builder().scheme(SCHEME)
                .authority(PACKAGE_NAME)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}