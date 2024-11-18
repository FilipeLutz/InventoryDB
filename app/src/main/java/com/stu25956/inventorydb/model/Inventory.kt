package com.stu25956.inventorydb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity class for the Inventory table
@Entity(tableName = "inventory")
// Data class for the Inventory table
data class Inventory(
    // Primary key for the Inventory table
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val quantity: Int,
    val supplier: String = "Co-op Store",
    val costPerUnit: Double
)