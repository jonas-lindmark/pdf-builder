package examples

import com.github.timrs2998.pdfbuilder.*
import com.github.timrs2998.pdfbuilder.style.Alignment
import com.github.timrs2998.pdfbuilder.style.Margin
import com.github.timrs2998.pdfbuilder.style.Padding
import io.kotest.core.spec.style.FunSpec
import javax.imageio.ImageIO
import org.apache.pdfbox.pdmodel.common.PDImmutableRectangle
import org.apache.pdfbox.pdmodel.font.Standard14Fonts

object VisitCardExample :
    FunSpec({ test("run VisitCardExample") { VisitCardExample.main(emptyArray()) } }) {

  @JvmStatic
  fun main(args: Array<String>) {
    document {
          val pageMargin = 4.mmToPdf()
          fontName = Standard14Fonts.FontName.HELVETICA
          margin = Margin(pageMargin, pageMargin, pageMargin, pageMargin)
          horizontalAlignment = Alignment.CENTER
          pdRectangle = PDImmutableRectangle(95.mmToPdf(), 54.mmToPdf())

          val img = this::class.java.classLoader.getResource("cat.jpg")

          val scaledImage =
              ImageIO.read(this::class.java.classLoader.getResource("cat.jpg"))
                  .resize(50.mmToPrint(300F), 12.mmToPrint(300F))

          for (i in 1..10) {
            vStack {
              hStack {
                horizontalAlignment = Alignment.CENTER
                image(scaledImage) {
                  imgWidth = scaledImage.width.pxToPdf(300F)
                  imgHeight = scaledImage.height.pxToPdf(300F)
                }
              }
              hStack {
                padding = Padding(top = 2.mmToPdf(), bottom = 2.mmToPdf())
                line(LineOrientation.HORIZONTAL, 80.mmToPdf())
              }
              hStack {
                weights.addAll(listOf(1F, 3F))
                qrCode("https://github.com") {
                  width = 22.mmToPdf()
                  height = 22.mmToPdf()
                }
                vStack {
                  padding = Padding(left = 2.mmToPdf())
                  text("Johan Doe") {
                    horizontalAlignment = Alignment.LEFT
                    fontSize = 8.mmToPdf()
                  }
                  text("Doe Company") {
                    fontSize = 4.mmToPdf()
                    horizontalAlignment = Alignment.LEFT
                  }
                }
              }
            }
            vStack {
              hStack {
                padding = Padding(top = 2.mmToPdf())
                fontSize = 3.mmToPdf()
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
