package duoc.cl.camara

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.io.FileOutputStream

class CameraStateHolder(
    val launcher: ActivityResultLauncher<Void?>
)

@Composable
fun rememberCameraStateHolder(
    onPhotoCaptured: (Uri?) -> Unit
): CameraStateHolder {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            try {
                val file = File(context.cacheDir, "captured_photo_${System.currentTimeMillis()}.jpg")
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                }
                onPhotoCaptured(Uri.fromFile(file))
            } catch (e: Exception) {
                onPhotoCaptured(null)
            }
        } else {
            onPhotoCaptured(null)
        }
    }

    return remember(launcher) {
        CameraStateHolder(launcher)
    }
}
