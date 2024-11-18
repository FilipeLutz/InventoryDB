package com.stu25956.inventorydb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// Entity class for the Inventory table
@Entity(tableName = "inventory", indices = [Index(value = ["name"], unique = true)])
// Data class to represent an inventory item
data class Inventory(
    // Primary key column
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // Column to store the name of the item
    @ColumnInfo(name = "name") val name: String,
    // Column to store the quantity of the item
    @ColumnInfo(name = "quantity") val quantity: Int,
    // Column to store the supplier of the item with default value
    @ColumnInfo(name = "supplier") val supplier: String = "Co-op Store",
    // Column to store the cost per unit of the item
    @ColumnInfo(name = "costPerUnit") val costPerUnit: Double
)