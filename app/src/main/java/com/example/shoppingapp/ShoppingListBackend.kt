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

// UI for ShoppingList App
class ShoppingAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingAppUI() // Renamed the UI function to avoid ambiguity
        }
    }
}

@Composable
fun ShoppingAppUI() { // Renamed function
    var newItem by remember { mutableStateOf("") }
    var newQuantity by remember { mutableStateOf("") }

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

        // Input for item name
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

        // Input for item quantity
        BasicTextField(
            value = newQuantity,
            onValueChange = { newQuantity = it.trim() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = { innerTextField ->
                if (newQuantity.isEmpty()) Text("Enter item quantity...")
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Add item button
        Button(onClick = {
            val quantity = newQuantity.toIntOrNull() ?: 0
            if (newItem.isNotBlank() && quantity > 0) {
                addItem(newItem, quantity)
                newItem = ""
                newQuantity = ""
            } else {
                println("Invalid item name or quantity!") // Debug log for invalid input
            }
        }) {
            Text("Add Item")
        }

        Spacer(modifier = Modifier.height(16.dp))

        shoppingList.forEach { item ->
            if (item.isEditing) {
                // Editing Mode: Editable fields for name and quantity
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var editedName by remember { mutableStateOf(item.name) }
                    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }

                    BasicTextField(
                        value = editedName,
                        onValueChange = { editedName = it.trim() },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    BasicTextField(
                        value = editedQuantity,
                        onValueChange = { editedQuantity = it.trim() },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {
                        val quantity = editedQuantity.toIntOrNull() ?: 0
                        if (editedName.isNotBlank() && quantity > 0) {
                            updateItem(item, editedName, quantity)
                        } else {
                            println("Invalid edits!") // Debug log for invalid edits
                        }
                    }) {
                        Text("Save")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = { toggleEditMode(item) }) {
                        Text("Cancel")
                    }
                }
            } else {
                // Normal Mode: Display item name and quantity
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${item.name} - ${item.quantity}")

                    Button(onClick = { toggleEditMode(item) }) {
                        Text("Edit")
                    }

                    Button(onClick = { removeItem(item) }) {
                        Text("Remove")
                    }
                }
            }
        }
    }
}
