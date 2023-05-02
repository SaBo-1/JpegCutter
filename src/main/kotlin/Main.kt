import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import java.awt.Color

fun main() {
    try {
        // Lade das Bild,                                      Hier den DateiPfad einsetzten
        val image: BufferedImage = ImageIO.read(File("/Users/sabo/Downloads/swordTwo.jpeg"))

        // Erstelle das Ausgabebild mit 32x32 Feldern
        val width = 128
        val height = 128
        val outputImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

        val blockWidth = image.width / width
        val blockHeight = image.height / height

        for (y in 0 until height) {
            for (x in 0 until width) {
                // Liste der Hex-Werte innerhalb des Blocks
                val hexValues = mutableListOf<String>()

                // Gehe durch jeden Pixel im Block
                for (j in 0 until blockHeight) {
                    for (i in 0 until blockWidth) {
                        val pixelX = x * blockWidth + i
                        val pixelY = y * blockHeight + j
                        if (pixelX < image.width && pixelY < image.height) {
                            val pixel = image.getRGB(pixelX, pixelY)
                            hexValues.add(String.format("#%06X", pixel and 0xFFFFFF))
                        }
                    }
                }

                // Finde den am häufigsten vorkommenden Hex-Wert im Block
                val mostCommonHexValue = hexValues.groupingBy { it }.eachCount().entries
                    .maxByOrNull { it.value }?.key

                // Wandle den Hex-Wert in einen RGB-Wert um
                val rgb = mostCommonHexValue?.let { Color.decode(it).rgb }

                // Setze die Farbe für den aktuellen Pixel im Ausgabebild
                if (rgb != null) {
                    outputImage.setRGB(x, y, rgb)
                }
            }
        }

        // Speichere das Ausgabebild als PNG-Datei            Ausgabe Name
        ImageIO.write(outputImage, "png", File("11.png"))

    } catch (e: IOException) {
        println("Fehler beim Laden der Bilddatei: ${e.message}")
    }
}