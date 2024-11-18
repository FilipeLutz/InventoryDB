package com.stu25956.inventorydb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.stu25956.inventorydb.database.DatabaseProvider
import com.stu25956.inventorydb.model.Inventory
import com.stu25956.inventorydb.screens.InventoryScreen
import com.stu25956.inventorydb.ui.theme.InventoryDatabaseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Test database initialization
        val db = DatabaseProvider.getDatabase(this)
        CoroutineScope(Dispatchers.IO).launch {
            val existingItem = db.inventoryDao().getInventoryByName("Red Wine BT")
            if (existingItem == null) {
                db.inventoryDao().insertInventory(
                    Inventory(name = "Red Wine BT", quantity = 109, costPerUnit = 6.50)
                )
            }
        }
        setContent {
            InventoryDatabaseTheme {
                Surface(color = MaterialTheme.colorScheme.primary) {
                    InventoryScreen()
                }
            }
        }
    }
}