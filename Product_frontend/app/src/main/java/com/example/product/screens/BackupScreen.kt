
package com.example.product.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

/*
data class MaterialItem(
    val material: String,
    val name: String,
    val type: String,
    val supplier: String,
    val passRate: Double,
    val goodRate: Double
)
@Composable
fun SettingsScreen () {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainScreen") {
        composable("mainScreen") {
            TraceabilityScreen(navController)
        }
        composable("secondScreen") {
            RawMaterialScreen(navController)
        }
    }

}
@Composable
fun InfoItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", fontSize = 16.sp, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, fontSize = 16.sp, style = MaterialTheme.typography.bodyLarge)
    }
}
@Composable
fun TraceabilityScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Text("基本信息", fontSize = 20.sp, style = MaterialTheme.typography.headlineSmall)
        }
        item {
            InfoItem(label = "ID", value = "2208165")
            InfoItem(label = "生产商", value = "华胜")
            InfoItem(label = "生产时间", value = "20220816")
            InfoItem(label = "生产地", value = "中岭")
            InfoItem(label = "质量等级", value = "1")
        }
        item {
            Text("成网 工艺信息", fontSize = 20.sp, style = MaterialTheme.typography.headlineSmall)
        }
        item {
            InfoItem(label = "名称", value = "湿法成网")
            InfoItem(label = "开始时间", value = "08160700")
            InfoItem(label = "结束时间", value = "08160734")
        }
        item {
            Text("固结 工艺信息", fontSize = 20.sp, style = MaterialTheme.typography.headlineSmall)
        }
        item {
            InfoItem(label = "名称", value = "射流喷网")
            InfoItem(label = "开始时间", value = "08160741")
            InfoItem(label = "结束时间", value = "08160823")
        }
        item {
            Text("后整理与复合 工艺信息", fontSize = 18.sp, style = MaterialTheme.typography.headlineSmall)
        }
        item {
            InfoItem(label = "名称", value = "功能性整理")
            InfoItem(label = "开始时间", value = "08160847")
            InfoItem(label = "结束时间", value = "08160902")
        }
        item {
            Text("检查记录", fontSize = 20.sp, style = MaterialTheme.typography.headlineSmall)
        }
        item {
            InfoItem(label = "检查开始时间", value = "08160937")
            InfoItem(label = "检查结束时间", value = "08161004")
            InfoItem(label = "检查结果", value = "合格")
            InfoItem(label = "检查责任人", value = "张三 ")
        }
        item {
            Button(onClick = { navController.navigate("secondScreen") }) {
                Text("查看原材料管理界面")
            }
        }
    }
}
@Composable
fun RawMaterialScreen(navController: NavController) {
    var createDial by remember { mutableStateOf(false) }
    val items = listOf(
        MaterialItem("1023", "纤维", "聚氯乙烯纤维", "荣成",99.7,93.2),
        MaterialItem("1024", "纤维", "聚酰胺纤维", "雅都",99.6,95.3),
        MaterialItem("1025", "粘合剂", "丁苯胶乳", "雅都",99.8,94.0)
    )
    Column(modifier = Modifier.padding(16.dp)) {
        Text("原材料管理", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("ID", modifier = Modifier.weight(1f))
                    Text("原材料名称", modifier = Modifier.weight(2f))
                }
            }
            items(items) { item ->
                MaterialRow(item)
            }
        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center)
        {
            Button(onClick = { navController.navigate("mainScreen") }) {
                Text("查看溯源界面")
            }
        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center)
        {
            Button(onClick = { createDial=true }) {
                Text("上传新的原材料")
            }
        }
    }
    if(createDial){
        CreateDialog(onDismiss = {createDial=false})
    }
}
@Composable
fun MaterialRow(item: MaterialItem) {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(item.material, modifier = Modifier.weight(1f))
        Text(item.name, modifier = Modifier.weight(1f))
        Button(onClick = { showDialog = true }) {
            Text("展开")
        }
    }
    if (showDialog) {
        InfoDialog(item = item, onDismiss = { showDialog = false })
    }
}
@Composable
fun InfoDialog(item: MaterialItem, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("原材料详细信息")
        },
        text = {
            Column {
                Text("ID: ${item.material}")
                Text("原材料名称: ${item.name}")
                Text("类别: ${item.type}")
                Text("供应商: ${item.supplier}")
                Text("合格率: ${item.passRate}%")
                Text("优品率: ${item.goodRate}%")
            }
        },
        confirmButton = {
            Button(onClick = { */
/* Implement delete functionality here *//*
 }) {
                Text("删除此原材料")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("返回")
            }
        }
    )
}
@Composable
fun CreateDialog(onDismiss: () -> Unit){
    var ID by remember { mutableStateOf("") }
    var Name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var supplier by remember { mutableStateOf("") }
    var passRate by remember { mutableStateOf("") }
    var goodRate by remember { mutableStateOf("") }
    var inspector by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("上传新的原材料")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = ID,
                    onValueChange =  {ID=it},
                    label = { Text("ID")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = Name,
                    onValueChange =  {Name=it},
                    label = { Text("原材料名称")}
                )
                OutlinedTextField(
                    value = type,
                    onValueChange =  {type=it},
                    label = { Text("类别")}
                )
                OutlinedTextField(
                    value = supplier,
                    onValueChange =  {supplier=it},
                    label = { Text("供应商")}
                )
                OutlinedTextField(
                    value = passRate,
                    onValueChange =  {passRate=it},
                    label = { Text("合格率")} ,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = goodRate,
                    onValueChange =  {goodRate=it},
                    label = { Text("优品率")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = inspector,
                    onValueChange =  {inspector=it},
                    label = { Text("审查人")}
                )
            }
        },
        confirmButton = {
            Button(onClick = {
 }) {
                Text("上传")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("返回")
            }
        }
    )
}

@ExperimentalPermissionsApi
@Composable
fun SettingsScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable(
            "home"
        ) {
            SearchScreen(navController)
        }
        composable(
            route = "results/{searchInput}",
            arguments = listOf(navArgument("searchInput") { type = NavType.StringType })
        ) { backStackEntry ->
            ResultsScreen(navController, backStackEntry.arguments?.getString("searchInput") ?: "")
        }
    }
}

@Composable
fun SearchScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }
    var showCamera by remember { mutableStateOf(false) }
    var barcodeResult by remember { mutableStateOf<String?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (showCamera) {
            CameraPreview(onBarcodeScanned = { barcode ->
                barcodeResult = barcode
                showCamera = false
                if (barcodeResult != null) {
                    navController.navigate("results/${barcodeResult}")
                }
            })
        } else {
            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("Find Run by id or Scan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                onClick = { if (text.isNotEmpty()) navController.navigate("results/${text}") },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Search by ID", style = MaterialTheme.typography.bodyLarge)
            }
            Button(onClick = { showCamera = true }) {
                Text("Start Scanning")
            }
        }
    }
}
@Composable
fun ResultsScreen(navController: NavController, searchInput: String, runViewModel: RunViewModel = viewModel()) {
    var runTitle by remember { mutableStateOf("Loading...") }
    var runMiles by remember { mutableStateOf(0) }

    LaunchedEffect(searchInput) {
        runViewModel.getRunById(searchInput.toInt()) { runDetails ->
            runTitle = runDetails.title
            runMiles = runDetails.miles
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text("$runTitle : $runMiles miles", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
@Composable
fun CameraPreview(onBarcodeScanned: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    var preview by remember { mutableStateOf<Preview?>(null) }

    AndroidView(
        factory = { AndroidViewContext ->
            PreviewView(AndroidViewContext).apply {
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { previewView ->
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val barcodeAnalyser = BarCodeAnalyser { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        barcodes.firstOrNull()?.rawValue?.let { barcodeValue ->
                            onBarcodeScanned(barcodeValue)
                            cameraProvider.unbindAll()
                            cameraExecutor.shutdown()
                        }
                    }
                }
                preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                    }

                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalysis
                )
            }, ContextCompat.getMainExecutor(context))
        }
    )
}*/

