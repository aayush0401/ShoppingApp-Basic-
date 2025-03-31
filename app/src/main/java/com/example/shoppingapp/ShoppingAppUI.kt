package com.example.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Main Activity for the Shopping List App
class ShoppingAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingApp()
        }
    }
}

@Composable
fun ShoppingApp() {
    var newItem by remember { mutableStateOf("") }
    val shoppingList = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            "🛒 Welcome to ShoppingList App!",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input field for entering item name
        BasicTextField(
            value = newItem,
            onValueChange = { newItem = it.trim() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = { innerTextField ->
                if (newItem.isEmpty()) Text("Enter item name...")
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Button to add item
        Button(onClick = {
            if (newItem.isNotBlank()) {
                shoppingList.add(newItem)
                newItem = "" // Clear input field after adding
            } else {
                println("Item name is blank!") // Debugging log
            }
        }) {
            Text("Add Item")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display shopping list
        shoppingList.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(item)

                Button(onClick = { shoppingList.remove(item) }) {
                    Text("Remove")
                }
            }
        }
    }
}
