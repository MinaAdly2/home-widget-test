// Import will depend on App ID.
package com.example.news_app

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.news_app.R

import java.io.File
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException

import android.content.res.AssetManager
import java.io.InputStream


// New import.
import es.antonborri.home_widget.HomeWidgetPlugin

/**
 * Implementation of App Widget functionality.
 */
class NewsWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        for (appWidgetId in appWidgetIds) {
            // Get reference to SharedPreferences.
            val widgetData = HomeWidgetPlugin.getData(context)
            val views = RemoteViews(context.packageName, R.layout.news_widget)

            // Set title and description if available in SharedPreferences
            val title = widgetData.getString("headline_title", null)
            views.setTextViewText(R.id.headline_title, title ?: "No title set")

            val description = widgetData.getString("headline_description", null)
            views.setTextViewText(R.id.headline_description, description ?: "No description set")

            // New: Add the section below to load an image into the widget if available
            val imageName = widgetData.getString("widget_image", null)
            if (imageName != null) {
                val imageFile = File(imageName)
                if (imageFile.exists()) {
                    val myBitmap: Bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
                    views.setImageViewBitmap(R.id.widget_image, myBitmap)
                } else {
                    println("Image not found! Looked @: $imageName")
                }
            } else {
                println("Image name is null!")
            }


// Load image using AssetManager

            //  if (imageName != null) {
            //     val assetManager: AssetManager = context.assets
            //     try {
            //         val inputStream: InputStream = assetManager.open("photo.jpg")
            //         val myBitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
            //         views.setImageViewBitmap(R.id.widget_image, myBitmap)
            //     } catch (e: IOException) {
            //         println("Image not found in assets! Looked @: assets/photo.jpg")
            //     }


                // try {
                //     val assetManager = context.assets
                //     assetManager.open(imageName).use { inputStream ->
                //         val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                //         views.setImageViewBitmap(R.id.widget_image, bitmap)
                //     }
                // } catch (e: IOException) {
                //     println("Image not found in assets! Looked @: $imageName")
                // }
            // } else {
            //     println("Image name is null!")
            // }

            // Update the widget with modified views
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
