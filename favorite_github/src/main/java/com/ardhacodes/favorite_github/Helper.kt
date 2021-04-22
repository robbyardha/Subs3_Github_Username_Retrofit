package com.ardhacodes.favorite_github

import android.database.Cursor

object Helper {
    fun mapCursorToArrayList (cursor: Cursor?) : ArrayList<Githubuser>
    {
        val Arrlist = ArrayList<Githubuser>()
        if (cursor != null){
            while (cursor.moveToNext()){
                val id = cursor.getColumnIndexOrThrow(BaseColumnDB.FavoriteUserColumn.ID)
                val username = cursor.getString(cursor.getColumnIndexOrThrow(BaseColumnDB.FavoriteUserColumn.USERNAME))
                val avatar_url = cursor.getString(cursor.getColumnIndexOrThrow(BaseColumnDB.FavoriteUserColumn.AVATAR_URL))
                val type = cursor.getString(cursor.getColumnIndexOrThrow(BaseColumnDB.FavoriteUserColumn.TYPE))

                Arrlist.add(
                    Githubuser(
                        username,
                        id,
                        avatar_url,
                        type
                    )
                )
            }
        }else{

        }
        return Arrlist
    }
}