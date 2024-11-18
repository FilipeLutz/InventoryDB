package com.stu25956.inventorydb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.stu25956.inventorydb.model.Inventory
import kotlinx.coroutines.flow.Flow

// The Data Access Object (DAO) for the Inventory table
@Dao
interface InventoryDao {
    // Insert a new inventory item
    @Insert
    suspend fun insertInventory(inventory: Inventory)

    // Delete an inventory item
    @Delete
    suspend fun deleteInventory(inventory: Inventory)

    // Select all inventory items
    @Query("SELECT * FROM inventory")
    fun getAllInventory(): Flow<List<Inventory>>

    // Get the total worth of all inventory items
    @Query("SELECT SUM(quantity * costPerUnit) FROM inventory")
    suspend fun getTotalWorth(): Double
}