package com.atv.launcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class HAReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Home Assistant"
        val message = intent.getStringExtra("message") ?: "Alert"
        val imageUrl = intent.getStringExtra("image") ?: ""
        
        val mainActivity = MainActivity.instance ?: return
        val haOverlay = mainActivity.findViewById<View>(R.id.haOverlay)
        val haTitle = mainActivity.findViewById<TextView>(R.id.haTitle)
        val haMessage = mainActivity.findViewById<TextView>(R.id.haMessage)
        val haImage = mainActivity.findViewById<ImageView>(R.id.haImage)
        
        haTitle.text = title
        haMessage.text = message
        
        if (imageUrl.isNotEmpty()) {
            Glide.with(mainActivity)
                .load(imageUrl)
                .into(haImage)
            haImage.visibility = View.VISIBLE
        } else {
            haImage.visibility = View.GONE
        }
        
        haOverlay.visibility = View.VISIBLE
        
        Handler(Looper.getMainLooper()).postDelayed({
            haOverlay.visibility = View.GONE
        }, 10000)
    }
}
