
package com.example.shoppingapp

import android.os.Bundle


import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.shoppingapp.ShoppingApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingApp()
        }
    }
}
