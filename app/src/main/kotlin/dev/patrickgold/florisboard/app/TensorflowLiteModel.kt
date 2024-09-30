import org.tensorflow.lite.Interpreter
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import android.content.Context

class OnDeviceTraining(context: Context) {
    private var interpreter: Interpreter

    init {
        interpreter = Interpreter(loadModelFile(context, "tensorflowLite/untrained_learn_typo_model.tflite"))
    }

    private fun loadModelFile(context: Context, modelPath: String): MappedByteBuffer {
        val assetFileDescriptor = context.assets.openFd(modelPath)
        val fileInputStream = assetFileDescriptor.createInputStream()
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun trainOnDevice(pressedCoordinates: FloatArray, targetCoordinates: FloatArray): Unit {
        val inputArray = arrayOf(pressedCoordinates)
        val outputArray = arrayOf(targetCoordinates)

        interpreter.run(inputArray, outputArray)
    }

    fun predictCoordinate(pressedCoordinates: FloatArray): FloatArray {
        val outputArray = Array(1) { FloatArray(2) }
        interpreter.run(arrayOf(pressedCoordinates), outputArray)

        return outputArray[0]
    }
}
