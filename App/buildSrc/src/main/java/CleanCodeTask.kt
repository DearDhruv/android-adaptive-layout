import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

open class CleanCodeTask : DefaultTask() {

    @get:InputDirectory
    var inputDir: File? = null

    @get:OutputDirectory
    var outputDir: File? = null

    init {
        description =
            "Cleans Kotlin files by replacing ', ,' and '(, ' and '( ,' with correct format"
        group = "cleaning"
    }

    @TaskAction
    fun clean() {
        val inputDirectory = inputDir
        val outputDirectory = outputDir

        if (inputDirectory == null || outputDirectory == null) {
            throw IllegalArgumentException("Input and output directories must be specified.")
        }

        if (!inputDirectory.exists() || !inputDirectory.isDirectory) {
            throw IllegalArgumentException("Input directory does not exist or is not a directory.")
        }
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs()
        }


        cleanDirectory(inputDirectory, outputDirectory)
    }

    private fun cleanDirectory(inputDirectory: File, outputDirectory: File) {
        inputDirectory.listFiles()?.forEach { file ->
            val outputFile = File(outputDirectory, file.name)
            if (file.isDirectory) {
                val newOutputDirectory = File(outputDirectory, file.name)
                newOutputDirectory.mkdirs()
                cleanDirectory(file, newOutputDirectory)
            } else if (file.isFile && file.extension == "kt") {
                cleanFile(file, outputFile)
            } else {
                copyFileOverwrite(file.toPath(), outputFile.toPath())
            }
        }
    }

    private fun cleanFile(inputFile: File, outputFile: File) {
        try {
            var content = inputFile.readText()
            content = content.replace(", , ", ", ")
            content = content.replace(", ,", ", ")
            content = content.replace("\\(, ".toRegex(), "(")
            content = content.replace("\\(,".toRegex(), "(")

            outputFile.writeText(content)
            // println("Cleaned file: ${inputFile.absolutePath}")
        } catch (e: Exception) {
            println("Error processing file: ${inputFile.absolutePath} - ${e.message}")
        }
    }

    fun copyFileOverwrite(source: Path, destination: Path) {
        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING)
            // println("File copied successfully to: ${destination.toAbsolutePath()}")
        } catch (e: IOException) {
            println("Error copying file: ${e.message}")
        }
    }
}