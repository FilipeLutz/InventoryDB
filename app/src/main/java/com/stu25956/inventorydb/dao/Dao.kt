package com.stu25956.inventorydb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stu25956.inventorydb.model.Inventory
import kotlinx.coroutines.flow.Flow

// The Data Access Object (DAO) for the Inventory table
@Dao
interface InventoryDao {

    // Insert an inventory item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInventory(inventory: Inventory)

    // Update an inventory item
    @Update
    suspend fun updateInventory(inventory: Inventory)

    // Delete an inventory item
    @Delete
    suspend fun deleteInventory(inventory: Inventory)

    // Select all inventory items
    @Query("SELECT * FROM inventory")
    fun getAllInventory(): Flow<List<Inventory>>

    // Get the total worth of all inventory items
    @Query("SELECT SUM(quantity * costPerUnit) FROM inventory")
    suspend fun getTotalWorth(): Double

    // Get an inventory item by name
    @Query("SELECT * FROM inventory WHERE name = :name LIMIT 1")
    suspend fun getInventoryByName(name: String): Inventory?
}
