package com.example.product
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.product.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {

                AppNavigation()

            }


        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}
@Composable
fun MainScreen(runViewModel: RunViewModel = viewModel()) {
    var runTitle by remember { mutableStateOf("Click the button to load run title") }

    // Modified Column to center its children
    Column(
        modifier = Modifier
            .fillMaxSize()  // Make Column fill the entire screen
            .padding(16.dp),  // Apply padding around the Column
        verticalArrangement = Arrangement.Center,  // Center children vertically
        horizontalAlignment = Alignment.CenterHorizontally  // Center children horizontally
    ) {
        // Text composable to display the run title
        Text(
            text = runTitle,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Button composable, functionality remains unchanged
        Button(onClick = {
            runViewModel.getRunById(2) { title ->
                runTitle = "  "/*RunDetails.title*/
            }
        }) {
            Text("Display")
        }
    }
}
/*@Composable
fun MainScreen(runViewModel: RunViewModel = viewModel()) {
    var runTitle by remember { mutableStateOf("Click the button to load run title") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = runTitle, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = {
            runViewModel.getRunById(1) { title ->
                runTitle = title
            }
        }) {
            Text("Display")
        }
    }
}*/




