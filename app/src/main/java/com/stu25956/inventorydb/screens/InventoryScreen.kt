package com.stu25956.inventorydb.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stu25956.inventorydb.database.DatabaseProvider
import com.stu25956.inventorydb.model.Inventory
import com.stu25956.inventorydb.ui.theme.Green
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Inventory Screen Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen() {
    // Get the database instance
    val context = LocalContext.current
    val db = remember { DatabaseProvider.getDatabase(context) }
    val inventoryDao = db.inventoryDao()

    // Mutable state variables to store the total worth and dialog visibility
    var totalWorth by remember { mutableDoubleStateOf(0.0) }
    var showDialog by remember { mutableStateOf(false) }
    var showAddItemDialog by remember { mutableStateOf(false) }

    // Mutable state variables to store the new item details
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    var itemSupplier by remember { mutableStateOf("") }
    var itemCost by remember { mutableStateOf("") }

    // Coroutine scope to launch coroutines
    val coroutineScope = rememberCoroutineScope()

    // Mutable state variable to store the inventory list
    var inventoryList by remember { mutableStateOf(listOf<Inventory>()) }

    // LaunchedEffect to collect the inventory list
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            inventoryDao.getAllInventory().collectLatest { list ->
                inventoryList = list
            }
        }
    }

    // Scaffold to provide the basic layout structure
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Inventory List",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                    )
                },
            )
        }
    ) { innerPadding ->
        // Main content inside the Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = innerPadding.calculateTopPadding())
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            // Inventory List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Display each item in the inventory list
                items(inventoryList) { item ->
                    // Inventory Item Card
                    InventoryItemCard(
                        item = item,
                        // Handle delete
                        onDeleteClick = { /* Handle delete */ },
                        // Handle update
                        onUpdateClick = { updatedItem ->
                            coroutineScope.launch {
                                withContext(Dispatchers.IO) {
                                    inventoryDao.updateInventory(updatedItem)
                                    inventoryList = inventoryDao.getAllInventory().first()
                                    totalWorth = inventoryDao.getTotalWorth()
                                }
                            }
                        }
                    )
                }

                // Button to add new item
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    // Add Item Button
                    Button(
                        modifier = Modifier
                            .padding(start = 16.dp),
                        onClick = { showAddItemDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green,
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Item",
                            tint = MaterialTheme.colorScheme.onSecondary,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Button to show total worth dialog at the bottom
            Button(
                onClick = {
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            totalWorth = inventoryDao.getTotalWorth()
                        }
                        showDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(80.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Total Worth",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
        }
    }

    // Display total worth in a dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = "Inventory Worth", style = MaterialTheme.typography.displaySmall)
            },
            text = {
                Column {
                    Text(
                        text = "The total worth of the inventory is:",
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    Text(
                        text = "€${"%.2f".format(totalWorth)}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Blue
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK",
                        fontSize = 25.sp)
                }
            }
        )
    }

    // Dialog to add new item
    if (showAddItemDialog) {
        AlertDialog(
            onDismissRequest = { showAddItemDialog = false },
            title = {
                Text("Add New Item",
                    style = MaterialTheme.typography.displaySmall) },
            text = {
                Column {
                    TextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        label = {
                            Text("Item Name",
                                fontSize = 18.sp) },
                        textStyle = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = itemQuantity,
                        onValueChange = { itemQuantity = it },
                        label = {
                            Text("Quantity",
                                fontSize = 18.sp) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = itemSupplier,
                        onValueChange = { itemSupplier = it },
                        label = {
                            Text("Supplier",
                                fontSize = 18.sp) },
                        textStyle = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = itemCost,
                        onValueChange = { itemCost = it },
                        label = {
                            Text("Cost per Unit",
                                fontSize = 18.sp) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Add the new item to the database
                        if (itemName.isNotEmpty() && itemQuantity.isNotEmpty() && itemCost.isNotEmpty()) {
                            val newItem = Inventory(
                                name = itemName,
                                quantity = itemQuantity.toInt(),
                                supplier = itemSupplier,
                                costPerUnit = itemCost.toDouble()
                            )
                            coroutineScope.launch {
                                withContext(Dispatchers.IO) {
                                    inventoryDao.insertInventory(newItem)
                                }
                            }
                        }
                        showAddItemDialog = false
                    }
                ) {
                    Text("Add",
                        fontSize = 25.sp)
                }
            },

            dismissButton = {
                TextButton(onClick = { showAddItemDialog = false }) {
                    Text("Cancel",
                        fontSize = 25.sp)
                }
            }
        )
    }
}