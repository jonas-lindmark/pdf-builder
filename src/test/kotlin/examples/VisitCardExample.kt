package examples

import org.apache.pdfbox.pdmodel.common.PDImmutableRectangle
import org.apache.pdfbox.pdmodel.font.Standard14Fonts
import se.denacode.pdfcompose.*
import se.denacode.pdfcompose.style.Alignment
import se.denacode.pdfcompose.style.Margin
import se.denacode.pdfcompose.style.Padding
import javax.imageio.ImageIO
import kotlin.test.Test

class VisitCardExample {

    @Test
    fun `Run VisitCardExample`() {
        document {
            val pageMargin = 4.mm()
            fontName = Standard14Fonts.FontName.HELVETICA
            margin = Margin(pageMargin, pageMargin, pageMargin, pageMargin)
            horizontalAlignment = Alignment.CENTER
            pdRectangle = PDImmutableRectangle(95.mm(), 54.mm())

            val scaledImage =
                ImageIO.read(this::class.java.classLoader.getResource("cat.jpg"))
                    .resize(50.mmToPrint(300f), 12.mmToPrint(300f))

            for (i in 1..10) {
                vStack {
                    hStack {
                        horizontalAlignment = Alignment.CENTER
                        image(scaledImage) {
                            imgWidth = scaledImage.width.pxToPdf(300f)
                            imgHeight = scaledImage.height.pxToPdf(300f)
                        }
                    }
                    hStack {
                        padding = Padding(top = 2.mm(), bottom = 2.mm())
                        line(LineOrientation.HORIZONTAL, 80.mm())
                    }
                    hStack {
                        weights.addAll(listOf(1f, 3f))
                        qrCode("https://github.com") {
                            width = 22.mm()
                            height = 22.mm()
                        }
                        vStack {
                            padding = Padding(left = 2.mm())
                            text("Johan Doe") {
                                horizontalAlignment = Alignment.LEFT
                                fontSize = 8.mm()
                            }
                            text("Doe Company") {
                                fontSize = 4.mm()
                                horizontalAlignment = Alignment.LEFT
                            }
                        }
                    }
                }
                vStack {
                    hStack {
                        padding = Padding(top = 2.mm())
                        fontSize = 3.mm()
                        text("1st street 123") { horizontalAlignment = Alignment.LEFT }
                        text("+46123456") { horizontalAlignment = Alignment.RIGHT }
                    }
                }
                pageBreak()
            }
        }
            .use { pdDocument -> pdDocument.save("src/test/kotlin/examples/VisitCardExample.pdf") }
    }
}
