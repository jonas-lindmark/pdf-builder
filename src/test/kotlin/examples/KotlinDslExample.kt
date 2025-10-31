package examples

import org.apache.pdfbox.pdmodel.font.Standard14Fonts
import se.denacode.pdfcompose.*
import se.denacode.pdfcompose.style.Alignment
import se.denacode.pdfcompose.style.Border
import se.denacode.pdfcompose.style.Margin
import java.awt.Color
import kotlin.test.Test

/**
 * Class containing main method to demonstrate creation of a sample "output.pdf" using a
 * Kotlin-specific DSL. This approach is more declarative, but is less portable between languages.
 */
class KotlinDslExample {

    @Test
    fun `Run KotlinDslExample`() {
        document {
            margin = Margin(50f, 50f, 50f, 50f)
            horizontalAlignment = Alignment.CENTER

            // Free image from https://pixabay.com/
            val img = this::class.java.classLoader.getResource("cat.jpg")
            image(img.path) {
                imgHeight = 50
                imgWidth = 90
            }

            text("Hello")

            text("Hello, color is red!") { fontColor = Color(1f, .1f, .1f) }

            // simulate table with stacks (wont handle pagebreak though)
            vStack {
                border = Border(1f, 2f, 3f, 4f, Color.GREEN, Color.RED, Color.BLUE, Color.BLACK)
                margin = Margin(25f, 0f, 25f, 0f)

                hStack {
                    backgroundColor = Color.CYAN
                    fontName = Standard14Fonts.FontName.TIMES_BOLD

                    text("First Column")
                    text("Second Column")
                }

                for (i in IntRange(0, 30)) {
                    hStack {
                        text("row #$i, col 0")
                        text("row #$i, col 1")
                    }
                }
            }

            pageBreak()
            image(img.path)

            text("Hola, mundo.")
        }
            .use { pdDocument -> pdDocument.save("src/test/kotlin/examples/KotlinDslExample.pdf") }
    }
}