package com.stu25956.inventorydb.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stu25956.inventorydb.model.Inventory

@Composable
// Inventory Item Card Composable
fun InventoryItemCard(
    item: Inventory,
    onDeleteClick: () -> Unit,
    onUpdateClick: (Inventory) -> Unit
) {
    // State to control the update dialog
    var showUpdateDialog by remember { mutableStateOf(false) }
    var updatedName by remember { mutableStateOf(item.name) }
    var updatedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var updatedSupplier by remember { mutableStateOf(item.supplier) }
    var updatedCost by remember { mutableStateOf(item.costPerUnit.toString()) }

    // Inventory Item Card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { showUpdateDialog = true },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Quantity: ${item.quantity}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Supplier: ${item.supplier}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cost per unit: €${"%.2f".format(item.costPerUnit)}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Total: €${"%.2f".format(item.quantity * item.costPerUnit)}",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Delete Icon Button
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(36.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Item",
                    tint = Color.Red,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }

    // Update Item Dialog
    if (showUpdateDialog) {
        AlertDialog(
            onDismissRequest = { showUpdateDialog = false },
            title = {
                Text("Update Item",
                style = MaterialTheme.typography.displaySmall) },
            text = {
                Column {
                    TextField(
                        value = updatedName,
                        onValueChange = { updatedName = it },
                        label = {
                            Text("Item Name:",
                                fontSize = 18.sp) },
                        textStyle = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = updatedQuantity,
                        onValueChange = { updatedQuantity = it },
                        label = {
                            Text("Quantity:",
                                fontSize = 18.sp) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = updatedSupplier,
                        onValueChange = { updatedSupplier = it },
                        label = {
                            Text("Supplier:",
                                fontSize = 18.sp) },
                        textStyle = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = updatedCost,
                        onValueChange = { updatedCost = it },
                        label = {
                            Text("Cost per Unit:",
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
                        // Validate and update the item
                        if (updatedName.isNotEmpty() &&
                            updatedQuantity.isNotEmpty() &&
                            updatedCost.isNotEmpty()
                        ) {
                            val updatedItem = item.copy(
                                name = updatedName,
                                quantity = updatedQuantity.toInt(),
                                supplier = updatedSupplier,
                                costPerUnit = updatedCost.toDouble()
                            )
                            onUpdateClick(updatedItem)
                        }
                        showUpdateDialog = false
                    }
                ) {
                    Text("Update",
                        fontSize = 25.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = { showUpdateDialog = false }) {
                    Text("Cancel",
                        fontSize = 25.sp)
                }
            }
        )
    }
}