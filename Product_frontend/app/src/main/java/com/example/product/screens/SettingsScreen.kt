package com.example.product.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.product.credential.CredentialViewModel
import kotlinx.coroutines.launch


data class Details(
    val ID:String,
    val name:String,
    val type:String,
    val supplier:String,
    val passRate:String,
    val goodRate:String
)
@Composable
fun SettingsScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainScreen") {
        composable("mainScreen") {
            TraceabilityScreen(navController)
        }
        composable("secondScreen") {
            RawMaterialScreen(navController)
        }
        composable("uploadNew") {
            UploadNew(navController )
        }
        composable(
            route = "DetailsScreen/{IDInput}",
            arguments = listOf(navArgument("IDInput") { type = NavType.StringType })
        ){backStackEntry ->
            DetailsScreen(backStackEntry.arguments?.getString("IDInput") ?: "" , navController )
        }
        composable("logout") {
            LogOutScreen(navController)
        }
        composable("login") {
            LogInScreen(navController)
        }
    }
}
@Composable
fun TraceabilityScreen(navController: NavController,credentialViewModel: CredentialViewModel=viewModel())
     {
         LaunchedEffect (Unit){
             credentialViewModel.getCredentialById(1){
                     credentialDetails ->
                 if (credentialDetails.isLoggedIn==1) {
                     navController.navigate("logout")
                 }
                 else {
                     navController.navigate("login")
                 }
             }
         }
}
@Composable
fun RawMaterialScreen(navController: NavController) {
    LazyColumn {
        item {
            IconButton(onClick = { navController.navigate("mainScreen")}) {
                Icon(Icons.Default.KeyboardArrowLeft,contentDescription = null)
            }
        }
        item {
            InfoUnit(
                title = "原材料管理",
                items = listOf(
                    "ID" to "原材料名称",
                )
            )
        }
        item { 
            MaterialUnit(title = "", items =
            listOf(
                "1023" to "纤维",
                "1024" to "纤维",
                "1025" to "粘合剂",
                "1026" to "粘合剂",
                "1027" to "纤维",
                "1028" to "粘合剂",
                "1029" to "纤维",
                "1030" to "纤维",
                "1031" to "粘合剂",
                "1032" to "纤维",
                "1033" to "纤维"
            ),navController
            )
        }
        item {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Button(onClick = { navController.navigate("uploadNew") },colors=ButtonDefaults.buttonColors(containerColor = Color.Green), shape = RoundedCornerShape(4.dp)) {
                    Text("上传新的原材料", color = Color.Black)
                }
            }
        }
    }
}
@Composable
fun LogInScreen(navController: NavController) {
    var showLoginDialog by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(false) }
    LazyColumn(Modifier.fillMaxSize())
    {
        item {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Button(onClick = { showLoginDialog= true },colors=ButtonDefaults.buttonColors(containerColor = Color.Green), shape = RoundedCornerShape(4.dp)) {
                    Text("登录", color = Color.Black)
                }
            }
        }
    }
    if(showLoginDialog){
        LoginDialog(onLoginSuccess = {
            isLoggedIn = true
            showLoginDialog = false
        }, onLoginFailed = {
            showLoginDialog = false
        },navController)
    }
}
@Composable
fun LogOutScreen(navController: NavController,credentialViewModel: CredentialViewModel= viewModel()) {
    var showLoginDialog by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(false) }
    Box (modifier = Modifier.fillMaxSize()){
        Button(onClick = {
            credentialViewModel.getCredentialById(1) {
                    credentialDetails ->
                val updatedCredentialDetails = credentialDetails.copy(isLoggedIn = 0)
                credentialViewModel.updateCredential(1, updatedCredentialDetails){

                }
            }
                         navController.navigate("login")},colors=ButtonDefaults.buttonColors(containerColor = Color.Red), shape = RoundedCornerShape(4.dp),modifier = Modifier.align(Alignment.Center)) {
            Text("退出登录", color = Color.White)
        }
    }
}
@Composable
fun LoginDialog(onLoginSuccess: () -> Unit, onLoginFailed: () -> Unit,navController: NavController,credentialViewModel: CredentialViewModel = viewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fetchName by remember { mutableStateOf("") }
    var fetchPassword by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Login") },
            text = {
                Column {
                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") }
                    )
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    credentialViewModel.getCredentialById(1) {
                            CredentialDetails ->
                        if (CredentialDetails.name == username && CredentialDetails.password == password) {
                            credentialViewModel.getCredentialById(1) {
                                    credentialDetails ->
                                val updatedCredentialDetails = credentialDetails.copy(isLoggedIn = 1)
                                credentialViewModel.updateCredential(1, updatedCredentialDetails){

                                }
                            }
                            showDialog = false
                            onLoginSuccess()
                            navController.navigate("logout")
                        }
                        else {
                            showDialog = false
                            onLoginFailed()
                        }
                    }
                }) {
                    Text("Log in")
                }
            }
        )
    }
}
@Composable
fun InfoUnit(title: String, items: List<Pair<String, String?>>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Title row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFFF0F0F0))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
        // Divider
        Divider(color = Color.LightGray, thickness = 1.dp)
        // Info items
        items.forEachIndexed { index, item ->
            InfoItem(title = item.first, subtitle = item.second)
            if (index < items.size - 1) {
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}
@Composable
fun InfoItem(title: String, subtitle: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
        if (subtitle != null) {
            Text(
                text = subtitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        }
    }
}
@Composable
fun MaterialUnit(title: String, items: List<Pair<String, String?>>,navController: NavController){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(color = Color.LightGray, thickness = 1.dp)
        items.forEachIndexed { index, item ->
            MaterialItem(title = item.first, subtitle = item.second, navController)
            if (index < items.size - 1) {
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}
@Composable
fun MaterialItem(title: String, subtitle: String?,navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
        if (subtitle != null) {
            Box(
                    modifier = Modifier
                        .clickable(
                            onClick = { navController.navigate("DetailsScreen/${title}") }
                        )
                        .padding(start = 8.dp)
                ) {
                Row {
                    Text(
                        text = subtitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Icon(Icons.Default.KeyboardArrowRight,contentDescription = null)
                }
                }

        }
    }
}
val SampleDetails1023 = Details(
    ID = "1023",
    name = "纤维",
    type = "聚乙烯纤维",
    supplier = "荣成",
    passRate = "99.7%",
    goodRate = "93.2%"
)
val SampleDetails1024 = Details(
    ID = "1024",
    name = "Item A",
    type = "Type 1",
    supplier = "Supplier X",
    passRate = "95%",
    goodRate = "90%"
)
val SampleDetails1025 = Details(
    ID = "1025",
    name = "Item A",
    type = "Type 1",
    supplier = "Supplier X",
    passRate = "95%",
    goodRate = "90%"
)
val sampleDetailsList = listOf(SampleDetails1023, SampleDetails1024, SampleDetails1025)
@Composable
fun DetailsScreen(IDInput: String,navController: NavController) {
    val details = sampleDetailsList.find { it.ID == IDInput }
    LazyColumn {
        item {
            IconButton(onClick = { navController.navigate("secondScreen")}) {
                Icon(Icons.Default.KeyboardArrowLeft,contentDescription = null)
            }
        }
     item {
         if (details != null) {
             InfoUnit(title = "原材料详细信息", items = listOf(
                 "ID" to details.ID,
                 "原材料名称" to details.name,
                 "类别" to details.type,
                 "供应商" to details.supplier,
                 "合格率" to details.passRate,
                 "优品率" to details.goodRate
             )
             )
         }
     }
        item {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Button(onClick = {  },colors=ButtonDefaults.buttonColors(containerColor = Color.Red), shape = RoundedCornerShape(4.dp)) {
                    Text("删除此原材料")
                }
            }
        }
    }
}
data class FormData(
    var name: String = "",
    var gender: String = ""
)
@Composable
fun UploadNew(navController: NavController) {
    var formData by remember { mutableStateOf(FormData()) }
    LazyColumn {
        item {
            IconButton(onClick = { navController.navigate("secondScreen")}) {
                Icon(Icons.Default.KeyboardArrowLeft,contentDescription = null)
            }
        }
        item {
            NameInputRow(name = formData.name, onNameChange = { formData = formData.copy(name = it) },"ID")
        }
    }
}
@Composable
fun NameInputRow(name: String, onNameChange: (String) -> Unit,ActualType: String) {
    var id by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var supplier by remember { mutableStateOf("") }
    var goodRate by remember { mutableStateOf("") }
    var passRate by remember { mutableStateOf("") }
    var inspector by remember { mutableStateOf("") }
    Column {
        Row {
            Text("ID")
            BasicTextField(
                value = id,
                onValueChange = { id=it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row {
            Text("原材料名称")
            BasicTextField(
                value = name,
                onValueChange = onNameChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row {
            Text("类别")
            BasicTextField(
                value = type,
                onValueChange = { type=it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row {
            Text("供应商")
            BasicTextField(
                value = supplier,
                onValueChange = { supplier=it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row {
            Text("合格率（%）")
            BasicTextField(
                value = passRate,
                onValueChange = { passRate=it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row {
            Text("优品率（%）")
            BasicTextField(
                value = goodRate,
                onValueChange = { goodRate=it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row {
            Text("审查负责人")
            BasicTextField(
                value = inspector,
                onValueChange = { inspector=it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Button(onClick = {  },colors=ButtonDefaults.buttonColors(containerColor = Color.Red), shape = RoundedCornerShape(4.dp), modifier = Modifier.padding(16.dp)) {
                Text("清空")
            }
            Button(onClick = {  },colors=ButtonDefaults.buttonColors(containerColor = Color.Blue), shape = RoundedCornerShape(4.dp),modifier = Modifier.padding(16.dp)) {
                Text("上传")
            }
        }
}   }