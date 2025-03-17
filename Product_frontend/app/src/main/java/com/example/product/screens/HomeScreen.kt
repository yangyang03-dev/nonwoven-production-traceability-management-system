package com.example.product.screens

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.product.RunViewModel
import androidx.compose.material3.TextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.product.barcode.BarCodeAnalyser
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Divider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.product.R
import com.example.product.product.ProductViewModel
import java.util.concurrent.Executors



@Composable
fun HomeScreen() {
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
        composable(
            route = "secondScreen/{IDInput}",
            arguments = listOf(navArgument("IDInput") { type = NavType.StringType })
        ) { backStackEntry ->
            RawDetailScreen(navController, backStackEntry.arguments?.getString("IDInput") ?: "")
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
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("输入ID来搜索") },
                    modifier = Modifier
                        .padding(4.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                IconButton(onClick = { if (text.isNotEmpty()) navController.navigate("results/${text}") }) {
                        Icon(Icons.Filled.Search, contentDescription = "null")
                }
            }
            Button(onClick = { showCamera = true },colors= ButtonDefaults.buttonColors(containerColor = Color.Blue),
                shape = RoundedCornerShape(4.dp)) {
                Text("扫描条形码")
            }
        }
    }
}

@Composable
fun ResultsScreen(navController: NavController, searchInput: String, productViewModel: ProductViewModel = viewModel()) {
    var productId by remember { mutableStateOf(0) }
    var productMCompany by remember { mutableStateOf("") }
    var productMDestination by remember { mutableStateOf("") }
    var productMQualityLevel by remember { mutableStateOf("") }
    var productMTime by remember { mutableStateOf("") }
    var productMaterialId1 by remember { mutableStateOf(0) }
    var productMaterialId2 by remember { mutableStateOf(0) }
    var productWsName1 by remember { mutableStateOf("") }
    var productWsStartTime1 by remember { mutableStateOf("") }
    var productWsEndTime1 by remember { mutableStateOf("") }
    var productWsName2 by remember { mutableStateOf("") }
    var productWsStartTime2 by remember { mutableStateOf("") }
    var productWsEndTime2 by remember { mutableStateOf("") }
    var productWsName3 by remember { mutableStateOf("") }
    var productWsStartTime3 by remember { mutableStateOf("") }
    var productWsEndTime3 by remember { mutableStateOf("") }
    var productInspectionStartTime by remember { mutableStateOf("") }
    var productInspectionEndTime by remember { mutableStateOf("") }
    var productInspectionResult by remember { mutableStateOf("") }
    var productResponsiblePerson by remember { mutableStateOf("") }
    LaunchedEffect(searchInput) {
        productViewModel.getProductById(searchInput.toInt()) { productDetails ->
            productId = productDetails.id
            productMCompany = productDetails.mCompany
            productMDestination = productDetails.mDestination
            productMQualityLevel = productDetails.mQualityLevel
            productMTime = productDetails.mTime
            productMaterialId1 = productDetails.materialId1
            productMaterialId2 = productDetails.materialId2
            productWsName1 = productDetails.wsName1
            productWsStartTime1 = productDetails.wsStartTime1
            productWsEndTime1 = productDetails.wsEndTime1
            productWsName2 = productDetails.wsName2
            productWsStartTime2 = productDetails.wsStartTime2
            productWsEndTime2 = productDetails.wsEndTime2
            productWsName3 = productDetails.wsName3
            productWsStartTime3 = productDetails.wsStartTime3
            productWsEndTime3 = productDetails.wsEndTime3
            productInspectionStartTime = productDetails.inspectionStartTime
            productInspectionEndTime = productDetails.inspectionEndTime
            productInspectionResult = productDetails.inspectionResult
            productResponsiblePerson = productDetails.responsiblePerson
        }
    }
    LazyColumn(Modifier.fillMaxSize())
    {
        item {
            IconButton(onClick = { navController.popBackStack()}) {
                Icon(Icons.Default.KeyboardArrowLeft,contentDescription = "back")
            }
        }
        item {
            InfoUnits(
                title = "基本信息",
                items = listOf(
                    "ID" to productId.toString(),
                    "生产商" to productMCompany,
                    "生产时间" to productMTime,
                    "生产地" to productMDestination,
                    "质量等级" to productMQualityLevel
                )
            )
        }
        item {
            InfoUnits(
                title = "原材料管理",
                items = listOf(
                )
            )
        }
        item {
            MaterialUnits(
                title = "原材料信息",
                items = listOf(
                    "纤维原材料ID" to productMaterialId1.toString(),
                    "粘合剂原材料ID" to productMaterialId2.toString(),
                ),navController
            )
        }
        item {
            InfoUnits(
                title = "成网 工艺信息",
                items = listOf(
                    "名称" to productWsName1,
                    "开始时间" to productWsStartTime1,
                    "结束时间" to productWsEndTime1
                )
            )
        }
        item {
            InfoUnits(
                title = "固结 工艺信息",
                items = listOf(
                    "名称" to productWsName2,
                    "开始时间" to productWsStartTime2,
                    "结束时间" to productWsEndTime2
                )
            )
        }
        item {
            InfoUnits(
                title = "后整理与复合 工艺信息",
                items = listOf(
                    "名称" to productWsName3,
                    "开始时间" to productWsStartTime3,
                    "结束时间" to productWsEndTime3
                )
            )
        }
        item {
            InfoUnits(
                title = "检查记录",
                items = listOf(
                    "检查开始时间" to productInspectionStartTime,
                    "检查结束时间" to productInspectionEndTime,
                    "检查结果" to productInspectionResult,
                    "检查责任人" to productResponsiblePerson
                )
            )
        }
    }
}
@Composable
fun InfoUnits(title: String, items: List<Pair<String, String?>>) {
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
            InfoItems(title = item.first, subtitle = item.second)
            if (index < items.size - 1) {
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}
@Composable
fun InfoItems(title: String, subtitle: String?) {
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
}