package com.stu25956.inventorydb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stu25956.inventorydb.dao.InventoryDao
import com.stu25956.inventorydb.model.Inventory

@Database(entities = [Inventory::class], version = 1)
// Abstract class to extend RoomDatabase
abstract class InventoryDatabase : RoomDatabase() {

    // Abstract method to provide access to InventoryDao
    abstract fun inventoryDao(): InventoryDao
}
// Object to provide the database instance
object DatabaseProvider {

    // Reference to the Room database
    private var INSTANCE: InventoryDatabase? = null

    // Function to get the database instance
    fun getDatabase(context: Context): InventoryDatabase {
        return INSTANCE ?: synchronized(this) {
            // Create the database instance if it doesn't exist
            val instance = Room.databaseBuilder(
                context.applicationContext,
                InventoryDatabase::class.java,
                "inventory_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}