package com.example.product.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.product.credential.CredentialViewModel
import com.example.product.material.MaterialDetails
import com.example.product.material.MaterialViewModel

@Composable
fun MScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainScreen") {
        composable("mainScreen") {
            RawScreen(navController)
        }
        composable(
            route = "secondScreen/{IDInput}",
            arguments = listOf(navArgument("IDInput") { type = NavType.StringType })
        ) { backStackEntry ->
            RawDetailScreen(navController, backStackEntry.arguments?.getString("IDInput") ?: "")
        }
        composable("uploadNew") {
            UploadNewMaterial(navController)
        }
    }
}
@Composable
fun RawScreen(navController: NavController,materialViewModel: MaterialViewModel = viewModel(),credentialViewModel: CredentialViewModel= viewModel()) {
    val materialList by materialViewModel.materialList.collectAsState()
    val items = materialList.map { it.id.toString() to it.title }
    var certificate by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        materialViewModel.getAllMaterials()
        credentialViewModel.getCredentialById(1){
            credentialDetails -> certificate=credentialDetails.isLoggedIn

        }
    }
    LazyColumn {
        item {
            InfoUnits(
                title = "原材料管理",
                items = listOf(
                    "ID" to "原材料名称",
                )
            )
        }
        item {
            MaterialUnits(title = "Material List", items = items, navController = navController)
        }
        item {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Button(onClick = { credentialViewModel.getCredentialById(1){
                    CredentialDetails -> certificate=CredentialDetails.isLoggedIn

                }
                    if (certificate==1) {
                    navController.navigate("uploadNew")
                }
                    else{
                        showDialog=true
                    }
                                 },colors= ButtonDefaults.buttonColors(containerColor = Color.Green), shape = RoundedCornerShape(4.dp)) {
                    Text("上传新的原材料", color = Color.Black)
                }
            }
        }
    }
    LoginPromptDialog(showDialog = showDialog, onDismiss = { showDialog = false })
}
@Composable
fun RawDetailScreen(navController: NavController,IDInput:String,materialViewModel: MaterialViewModel= viewModel(),credentialViewModel: CredentialViewModel= viewModel()) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var certificate by remember { mutableStateOf(0) }
    var materialId by remember { mutableStateOf(0) }
    var materialTitle by remember { mutableStateOf("") }
    var materialType by remember { mutableStateOf("") }
    var materialSupplier by remember { mutableStateOf("") }
    var materialPassRate by remember { mutableStateOf(0.0f) }
    var materialGoodRate by remember { mutableStateOf(0.0f) }
    var materialQualityLevel by remember { mutableStateOf("") }
    var materialInspector by remember { mutableStateOf("") }
    LaunchedEffect (IDInput){
        materialViewModel.getMaterialById(IDInput.toInt()) {
            materialDetails ->
            materialId=materialDetails.id
            materialTitle=materialDetails.title
            materialType=materialDetails.type
            materialSupplier=materialDetails.supplier
            materialPassRate=materialDetails.passRate
            materialGoodRate=materialDetails.goodRate
            materialQualityLevel=materialDetails.qualityLevel
            materialInspector=materialDetails.inspector
        }
        credentialViewModel.getCredentialById(1){
                credentialDetails -> certificate=credentialDetails.isLoggedIn

        }
    }
    LazyColumn (Modifier.fillMaxSize()) {
        item {
            IconButton(onClick = { navController.popBackStack()}) {
                Icon(Icons.Default.KeyboardArrowLeft,contentDescription = null)
            }
        }
        item {
                InfoUnit(title = "原材料详细信息", items = listOf(
                    "ID" to materialId.toString(),
                    "原材料名称" to materialTitle,
                    "类别" to materialType,
                    "供应商" to materialSupplier,
                    "合格率" to materialPassRate.toString(),
                    "优品率" to materialGoodRate.toString(),
                    "质量等级" to materialQualityLevel,
                    "审查负责人" to materialInspector
                )
                )
        }
        item {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Button(onClick = { credentialViewModel.getCredentialById(1){
                        CredentialDetails -> certificate=CredentialDetails.isLoggedIn

                }
                    if (certificate==1) {
                        materialViewModel.deleteMaterial(materialId){ isSuccess ->
                            if (isSuccess) {
                                showConfirmDialog=true;
                            }
                        }
                    }
                    else{
                        showDialog=true
                    }},colors=ButtonDefaults.buttonColors(containerColor = Color.Red), shape = RoundedCornerShape(4.dp)) {
                    Text("删除此原材料")
                }
            }
        }
    }
    if (showConfirmDialog) {
        ConfirmationDialog("已成功删除") {
        }
    }
    LoginPromptDialog(showDialog = showDialog, onDismiss = { showDialog = false })
}
@Composable
fun UploadNewMaterial(navController: NavController,viewModel: MaterialViewModel= viewModel()) {
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var supplier by remember { mutableStateOf("") }
    var qualityLevel by remember { mutableStateOf("") }
    var goodRate by remember { mutableStateOf("") }
    var passRate by remember { mutableStateOf("") }
    var inspector by remember { mutableStateOf("") }
    LazyColumn {
        item {
            IconButton(onClick = { navController.navigate("mainScreen")}) {
                Icon(Icons.Default.KeyboardArrowLeft,contentDescription = null)
            }
        }
        item {
            Column {
                Row {
                    Text("ID")
                    BasicTextField(
                        value = id,
                        onValueChange = { id = it },
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
                        onValueChange = { name=it },
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
                        onValueChange = { type = it },
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
                        onValueChange = { supplier = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)
                Row {
                    Text("质量等级")
                    BasicTextField(
                        value = qualityLevel,
                        onValueChange = { qualityLevel = it },
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
                        onValueChange = { passRate = it },
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
                        onValueChange = { goodRate = it },
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
                        onValueChange = { inspector = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("清空")
                    }
                    Button(
                        onClick = {
                           var idInt =id.toIntOrNull()
                            var passRateFloat=passRate.toFloatOrNull()
                            var goodRateFloat =goodRate.toFloatOrNull()
                            if (idInt!=null && passRateFloat!=null && goodRateFloat!=null && name.isNotBlank() && type.isNotBlank() && supplier.isNotBlank() && inspector.isNotBlank() && qualityLevel.isNotBlank()){
                                viewModel.createMaterial(MaterialDetails(idInt,name,type,supplier,passRateFloat,goodRateFloat,qualityLevel,inspector)){

                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("上传")
                    }
                }
            }
        }
    }
}
@Composable
fun MaterialUnits(title: String, items: List<Pair<String, String?>>, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(color = Color.LightGray, thickness = 1.dp)
        items.forEachIndexed { index, item ->
            MaterialItems(title = item.first, subtitle = item.second, navController)
            if (index < items.size - 1) {
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun MaterialItems(title: String, subtitle: String?,navController: NavController){
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
                        onClick = { if (title.toIntOrNull()!=null){navController.navigate("secondScreen/${title}")}
                        else {navController.navigate("secondScreen/${subtitle}")}}
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
@Composable
fun ConfirmationDialog(
    dialogText:String,
    onDismissRequest: () -> Unit
) {
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {  },
            confirmButton = { Button(onClick = {showDialog=false ;onDismissRequest()},colors=ButtonDefaults.buttonColors(containerColor = Color.Blue), shape = RoundedCornerShape(4.dp)) {
                Text("确定")
            }},
            title = {Text(text = "信息")},
            text = {
                Text(
                    text = dialogText,
                )
            },
        )
    }
}
@Composable
fun LoginPromptDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = "用户权限不足")
            },
            text = {
                Text("请登录后继续操作")
            },
            confirmButton = {
                Button(onClick = onDismiss,colors= ButtonDefaults.buttonColors(containerColor = Color.Blue), shape = RoundedCornerShape(4.dp)) {
                    Text("确定")
                }
            }
        )
    }
}
