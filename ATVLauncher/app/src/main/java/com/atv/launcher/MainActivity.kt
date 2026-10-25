package com.atv.launcher

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        var instance: MainActivity? = null
    }
    
    private val wallpapers = listOf(
        "https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=1920",
        "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=1920",
        "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=1920",
        "https://images.unsplash.com/photo-1418065460487-3e41a6c84dc5?w=1920",
        "https://images.unsplash.com/photo-1472214103451-9374bd1c798e?w=1920"
    )
    
    private var wallpaperIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    
    override onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        setContentView(R.layout.activity_main)
        
        val backgroundImage = findViewById<ImageView>(R.id.backgroundImage)
        val appsRecycler = findViewById<RecyclerView>(R.id.appsRecycler)
        val haOverlay = findViewById<View>(R.id.haOverlay)
        haOverlay.visibility = View.GONE
        
        appsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        
        CoroutineScope(Dispatchers.Main).launch {
            val apps = AppsLoader(this@MainActivity).loadApps()
            val adapter = AppsAdapter(apps) { app ->
                val launchIntent = packageManager.getLaunchIntentForPackage(app.packageName)
                launchIntent?.let { startActivity(it) }
            }
            appsRecycler.adapter = adapter
            if (apps.isNotEmpty()) {
                appsRecycler.post { appsRecycler.getChildAt(0)?.requestFocus() }
            }
        }
        
        cycleWallpaper(backgroundImage)
    }
    
    private fun cycleWallpaper(imageView: ImageView) {
        Glide.with(this)
            .load(wallpapers[wallpaperIndex])
            .centerCrop()
            .into(imageView)
        
        wallpaperIndex = (wallpaperIndex + 1) % wallpapers.size
        handler.postDelayed({ cycleWallpaper(imageView) }, 30000)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        instance = null
        handler.removeCallbacksAndMessages(null)
    }
    
    override fun onBackPressed() {
        // Stay on home screen
    }
}
