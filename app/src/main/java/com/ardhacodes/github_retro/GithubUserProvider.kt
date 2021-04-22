package com.ardhacodes.github_retro

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class GithubUserProvider : ContentProvider() {

    companion object{
        const val AUTHORITY = "com.ardhacodes.github_retro"
        const val TABLE_NAME = "table_favoriteuser" //Nama Table Favorit User di FavoriteUserEntity
        const val ID_FAVORITE_USER_DATA = 1
        val uriSegmentMatcher = UriMatcher(UriMatcher.NO_MATCH)
    }

    private lateinit var interfaceQuery : FavoriteUserInterfaceQueries

    init {
        uriSegmentMatcher.addURI(AUTHORITY, TABLE_NAME, ID_FAVORITE_USER_DATA)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
       return null
    }

    override fun onCreate(): Boolean {
        interfaceQuery = context?.let {context ->
            GithubUserDB.getDatabase(context)?.favoriteUserInterfaceQueries()
        }!!
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var cursor: Cursor?
        when(uriSegmentMatcher.match(uri)){
            ID_FAVORITE_USER_DATA -> {
                cursor = interfaceQuery.FindAllData()
                if (context != null){
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }else{
                    cursor = null
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
//        return null
        return 0
    }
}