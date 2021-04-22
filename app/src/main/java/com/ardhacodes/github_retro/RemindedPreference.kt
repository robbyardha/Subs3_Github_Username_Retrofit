package com.ardhacodes.github_retro

import android.content.Context

class RemindedPreference(context: Context) {
    companion object{
        const val PREFERENCE_NAME = "Reminder Preference"
        private const val REMINDER = "isReminder"

    }

    private val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: RemindedData)
    {
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value.ThisisRemainded)
        editor.apply()
    }

    fun getReminder() : RemindedData
    {
        val model = RemindedData()
        model.ThisisRemainded = preference.getBoolean(REMINDER, false)
        return model
    }
}