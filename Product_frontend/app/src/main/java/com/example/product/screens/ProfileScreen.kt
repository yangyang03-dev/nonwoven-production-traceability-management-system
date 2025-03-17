package com.example.product.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.product.RunDetails
import com.example.product.RunViewModel
import android.content.Context
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.product.credential.CredentialDetails
import com.example.product.credential.CredentialViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import org.json.JSONObject

@Composable
fun ProfileScreen() {
    var isLoggedIn by remember { mutableStateOf(false) }
    var showLoginDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val fileName = "config.json"
    if (isLoggedIn) {
        UserProfile()  // The UI that shows the user's profile
    } else {
        
        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = {showLoginDialog= true},
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text("Log in")
            }
        }
        if(showLoginDialog){
            LoginDialog(onLoginSuccess = {
                isLoggedIn = true
                showLoginDialog = false
                scope.launch {
                    onLoginSuccess(context, fileName)
                }
            }, onLoginFailed = {
                showLoginDialog = false
            }, navController= rememberNavController())
        }
    }
}

fun readFromFile(context: Context, fileName: String): JSONObject {
    val file = File(context.filesDir, fileName)
    if (!file.exists()) {
        file.writeText("{\"logged\":false}")
    }
    return JSONObject(file.readText())
}

fun writeToFile(context: Context, fileName: String, jsonObject: JSONObject) {
    val file = File(context.filesDir, fileName)
    file.writeText(jsonObject.toString())
}
suspend fun onLoginSuccess(context: Context, fileName: String) {
    val jsonObject = readFromFile(context, fileName)
    jsonObject.put("logged", true)
    withContext(Dispatchers.IO) {
        writeToFile(context, fileName, jsonObject)
    }
}
@Composable
fun UserProfile(runViewModel: RunViewModel = viewModel()) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    , verticalArrangement = Arrangement.Center) {
        Button(onClick = { showCreateDialog = true }) {
            Text("Create Run")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { showDeleteDialog = true }) {
            Text("Delete Run")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { showUpdateDialog = true }) {
            Text("Update Run")
        }

        if (showCreateDialog) {
            CreateRunDialog(viewModel = runViewModel, onDismiss = { showCreateDialog = false })
        }

        if (showDeleteDialog) {
            DeleteRunDialog(viewModel = runViewModel, onDismiss = { showDeleteDialog = false })
        }

        if (showUpdateDialog) {
            UpdateRunDialog(viewModel = runViewModel, onDismiss = { showUpdateDialog = false })
        }
    }
}
@Composable
fun CreateRunDialog(viewModel: RunViewModel, onDismiss: () -> Unit) {
    var id by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var miles by remember { mutableStateOf("") }
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Create New Run") },
        text = {
            Column {
                OutlinedTextField(
                    value = id,
                    onValueChange ={id = it},
                    label= { Text("ID")}
                    )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                OutlinedTextField(
                    value = miles,
                    onValueChange = { miles = it },
                    label = { Text("Miles") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    var idInt = id.toIntOrNull()
                    val milesInt = miles.toIntOrNull()
                    if (title.isNotBlank() && milesInt != null && idInt!= null) {
                        viewModel.createRun(RunDetails(title, milesInt),idInt) { result ->
                            Toast.makeText(context, "Run created: ${result.title}", Toast.LENGTH_SHORT).show()
                            title = ""
                            miles = ""
                            onDismiss()
                        }
                    } else {
                        Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
@Composable
fun DeleteRunDialog(viewModel: RunViewModel, onDismiss: () -> Unit) {
    var runId by remember { mutableStateOf("") }
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Delete Run") },
        text = {
            Column {
                OutlinedTextField(
                    value = runId,
                    onValueChange = { runId = it },
                    label = { Text("Run ID") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(
                {
                    val idInt = runId.toIntOrNull()
                    if (idInt != null) {
                        viewModel.deleteRun(idInt) { success ->
                            if (success) {
                                Toast.makeText(context, "Run deleted successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Failed to delete run", Toast.LENGTH_SHORT).show()
                            }
                            onDismiss()
                        }
                    } else {
                        Toast.makeText(context, "Invalid ID", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
@Composable
fun UpdateRunDialog(viewModel: RunViewModel, onDismiss: () -> Unit) {
    var runId by remember { mutableStateOf("") }
    var newTitle by remember { mutableStateOf("") }
    var newMiles by remember { mutableStateOf("") }
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Update Run") },
        text = {
            Column {
                OutlinedTextField(
                    value = runId,
                    onValueChange = { runId = it },
                    label = { Text("Run ID") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = newTitle,
                    onValueChange = { newTitle = it },
                    label = { Text("New Title") }
                )
                OutlinedTextField(
                    value = newMiles,
                    onValueChange = { newMiles = it },
                    label = { Text("New Miles") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val idInt = runId.toIntOrNull()
                    val milesInt = newMiles.toIntOrNull()
                    if (idInt != null && (newTitle.isNotBlank() || milesInt != null)) {
                        viewModel.updateRun(idInt, newTitle, milesInt) { details ->
                            Toast.makeText(context, "Run updated successfully: ${details.title}", Toast.LENGTH_SHORT).show()
                            onDismiss()
                        }
                    } else {
                        Toast.makeText(context, "Invalid input for updating", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

