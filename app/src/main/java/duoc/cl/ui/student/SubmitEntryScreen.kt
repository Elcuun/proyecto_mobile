package duoc.cl.ui.student

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import duoc.cl.camara.rememberCameraStateHolder
import duoc.cl.data.local.LogbookEntity
import duoc.cl.data.local.User
import duoc.cl.viewmodel.StudentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitEntryScreen(
    logbookId: Long,
    currentUser: User?,
    viewModel: StudentViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    var logbook by remember { mutableStateOf<LogbookEntity?>(null) }

    LaunchedEffect(logbookId) {
        logbook = viewModel.getLogbookById(logbookId)
    }

    val currentLogbook = logbook
    if (currentLogbook == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val context = LocalContext.current
    val formState by viewModel.entryFormState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(currentUser) {
        if (currentUser != null && formState.studentName.isBlank()) {
            viewModel.onStudentNameChanged(currentUser.fullName)
        }
    }

    val cameraController = rememberCameraStateHolder(
        onPhotoCaptured = { uri ->
            viewModel.setPhotoUri(uri?.toString())
        }
    )

    val photoBitmap = remember(formState.photoUri) {
        val uriStr = formState.photoUri ?: return@remember null
        try {
            val uri = Uri.parse(uriStr)
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            null
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraController.launcher.launch(null)
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            val fusedClient = LocationServices.getFusedLocationProviderClient(context)
            try {
                fusedClient.lastLocation.addOnSuccessListener { loc ->
                    if (loc != null) {
                        viewModel.setLocation(loc.latitude, loc.longitude)
                    }
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Bitácora Mensual") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = currentLogbook.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Propósito: ${currentLogbook.purpose}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Instrucciones: ${currentLogbook.instructions}", style = MaterialTheme.typography.bodySmall)
                    if (currentLogbook.guidingQuestions.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Pregunta Guía: ${currentLogbook.guidingQuestions}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            OutlinedTextField(
                value = formState.studentName,
                onValueChange = viewModel::onStudentNameChanged,
                label = { Text("Nombre y Apellido del Estudiante") },
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                isError = formState.studentNameError != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            AnimatedVisibility(visible = formState.studentNameError != null) {
                Text(
                    text = formState.studentNameError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = formState.contents,
                onValueChange = viewModel::onContentsChanged,
                label = { Text("Contenidos Trabajados en Clase") },
                leadingIcon = { Icon(imageVector = Icons.Default.Book, contentDescription = null) },
                isError = formState.contentsError != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 2
            )
            AnimatedVisibility(visible = formState.contentsError != null) {
                Text(
                    text = formState.contentsError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = formState.activities,
                onValueChange = viewModel::onActivitiesChanged,
                label = { Text("Actividades Realizadas") },
                leadingIcon = { Icon(imageVector = Icons.Default.Assignment, contentDescription = null) },
                isError = formState.activitiesError != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 2
            )
            AnimatedVisibility(visible = formState.activitiesError != null) {
                Text(
                    text = formState.activitiesError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = formState.learnings,
                onValueChange = viewModel::onLearningsChanged,
                label = { Text("Aprendizajes Obtenidos") },
                leadingIcon = { Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null) },
                isError = formState.learningsError != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 2
            )
            AnimatedVisibility(visible = formState.learningsError != null) {
                Text(
                    text = formState.learningsError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = formState.doubts,
                onValueChange = viewModel::onDoubtsChanged,
                label = { Text("Dudas o Dificultades (Opcional)") },
                leadingIcon = { Icon(imageVector = Icons.Default.HelpOutline, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 2
            )

            Text(text = "Recursos del Dispositivo", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        val permissionCheck = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        )
                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                            cameraController.launcher.launch(null)
                        } else {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.CameraAlt, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (formState.photoUri != null) "Foto OK" else "Tomar Foto")
                }

                OutlinedButton(
                    onClick = {
                        val fineLocation = Manifest.permission.ACCESS_FINE_LOCATION
                        val coarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION
                        if (ContextCompat.checkSelfPermission(context, fineLocation) == PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(context, coarseLocation) == PackageManager.PERMISSION_GRANTED
                        ) {
                            val fusedClient = LocationServices.getFusedLocationProviderClient(context)
                            try {
                                fusedClient.lastLocation.addOnSuccessListener { loc ->
                                    if (loc != null) {
                                        viewModel.setLocation(loc.latitude, loc.longitude)
                                    }
                                }
                            } catch (e: SecurityException) {
                                e.printStackTrace()
                            }
                        } else {
                            locationPermissionLauncher.launch(arrayOf(fineLocation, coarseLocation))
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (formState.latitude != null) "GPS OK" else "Ubicación GPS")
                }
            }

            AnimatedVisibility(visible = photoBitmap != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (photoBitmap != null) {
                        Image(
                            bitmap = photoBitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            AnimatedVisibility(visible = formState.latitude != null) {
                Text(
                    text = "Coordenadas GPS: ${formState.latitude}, ${formState.longitude}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.submitEntry(currentLogbook.id) { success ->
                        if (success) onSuccess()
                    }
                },
                enabled = formState.isFormValid && !currentLogbook.isClosed,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = if (currentLogbook.isClosed) "Bitácora Cerrada" else "Enviar Registro Mensual")
            }
        }
    }
}
