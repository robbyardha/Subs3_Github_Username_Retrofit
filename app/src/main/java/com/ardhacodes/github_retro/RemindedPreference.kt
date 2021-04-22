package com.ardhacodes.github_retro

import android.content.Context

class RemindedPreference(context: Context) {
    companion object{
        const val PREFERENCE_NAME = "Notification Preference"
        private const val REMINDER = "This Reminder"

    }

    private val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: RemindedData)
    {
        val editable = preference.edit()
        editable.putBoolean(REMINDER, value.ThisisRemainded)
        editable.apply()
    }

    fun getReminder() : RemindedData
    {
        val GetModelData = RemindedData()
        GetModelData.ThisisRemainded = preference.getBoolean(REMINDER, false)
        return GetModelData
    }
}