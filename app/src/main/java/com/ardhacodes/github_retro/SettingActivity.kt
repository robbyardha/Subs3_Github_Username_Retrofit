package com.ardhacodes.github_retro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardhacodes.github_retro.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var reminder: RemindedData
    private lateinit var notifReceiver: NotificationReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        TitleActionbar()
        val RemindedPreference = RemindedPreference(this)
        if (RemindedPreference.getReminder().ThisisRemainded){
            binding.SwitchButton.isChecked = true
        }else{
            binding.SwitchButton.isChecked = false
        }

        notifReceiver = NotificationReceiver()
        binding.SwitchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                saveReminder(true)
                //variable to setting time
                var type = "Github Notification Alarm"
                var time = "09:00"
                var message = "Find Github Username"
                notifReceiver.setMessageNotification(this, type, time, message)
            }else{
                saveReminder(false)
                notifReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(state: Boolean)
    {
        val RemindedPreference = RemindedPreference(this)
        reminder = RemindedData()

        reminder.ThisisRemainded = state
        RemindedPreference.setReminder(reminder)
    }

    private fun TitleActionbar()
    {
        val actionBar = supportActionBar
        actionBar?.title = "Setting Notification"
    }
}