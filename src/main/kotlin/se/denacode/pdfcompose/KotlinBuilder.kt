package se.denacode.pdfcompose

import java.awt.image.BufferedImage
import org.apache.pdfbox.pdmodel.PDDocument
import se.denacode.pdfcompose.elements.ContainerElement
import se.denacode.pdfcompose.elements.Document
import se.denacode.pdfcompose.elements.HorisontalStackElement
import se.denacode.pdfcompose.elements.ImageElement
import se.denacode.pdfcompose.elements.PageBreakSpacerElement
import se.denacode.pdfcompose.elements.QrCodeElement
import se.denacode.pdfcompose.elements.TextElement
import se.denacode.pdfcompose.elements.VerticalStackElement

const val MILLIMETER_PER_INCH = 25.4
const val PDF_POINTS_PER_INCH = 72.0
const val MILLIMETER_TO_PDF_POINTS = PDF_POINTS_PER_INCH / MILLIMETER_PER_INCH

/** Convert millimeters to PDF points */
fun Int.mm(): Float = (this.toDouble() * MILLIMETER_TO_PDF_POINTS).toFloat()

/** Convert mm on print to "pixels" at given DPI */
fun Int.mmToPrint(dpi: Float): Int = (this.toDouble() * dpi / MILLIMETER_PER_INCH).toInt()

/** Convert pixels at given DPI to DPF points on print */
fun Int.pxToPdf(dpi: Float): Int = (this.toDouble() * PDF_POINTS_PER_INCH / dpi).toInt()

/** A DSL for Kotlin, Groovy or Java 8 consumers of this API. */
@DslMarker annotation class DocumentMarker

/**
 * Creates the outermost [com.github.timrs2998.pdfbuilder.elements.Document]
 * [element][com.github.timrs2998.pdfbuilder.elements.Element] representing the pdf, and returns the
 * rendered [PDDocument] that can be [saved][PDDocument.save] to a [java.io.File] or
 * [java.io.OutputStream].
 *
 * @return The rendered [PDDocument].
 */
fun document(init: Document.() -> Unit): PDDocument {
  val document = Document()
  document.init()
  return document.render()
}

@DocumentMarker
fun Document.pageBreak(): PageBreakSpacerElement {
  val element = PageBreakSpacerElement(this)
  addContainerChild(element)
  return element
}

@DslMarker annotation class ContainerElementMarker

@ContainerElementMarker
fun ContainerElement.text(value: String, init: TextElement.() -> Unit = {}): TextElement {
  val textElement = TextElement(this, value)
  textElement.init()
  addContainerChild(textElement)
  return textElement
}

@ContainerElementMarker
fun ContainerElement.image(imagePath: String, init: ImageElement.() -> Unit = {}): ImageElement {
  val imageElement = ImageElement(this, imagePath, null)
  imageElement.init()
  addContainerChild(imageElement)
  return imageElement
}

@ContainerElementMarker
fun ContainerElement.image(
    bufferedImage: BufferedImage,
    init: ImageElement.() -> Unit = {}
): ImageElement {
  val imageElement = ImageElement(this, "", bufferedImage)
  imageElement.init()
  addContainerChild(imageElement)
  return imageElement
}

@ContainerElementMarker
fun ContainerElement.qrCode(content: String, init: QrCodeElement.() -> Unit = {}): QrCodeElement {
  val qrCodeElement = QrCodeElement(this, content)
  qrCodeElement.init()
  addContainerChild(qrCodeElement)
  return qrCodeElement
}

@ContainerElementMarker
fun ContainerElement.line(
    orientation: LineOrientation,
    length: Float,
    width: Float = 1F,
    init: LineElement.() -> Unit = {}
): LineElement {
  val lineElement = LineElement(this, orientation, length, width)
  lineElement.init()
  addContainerChild(lineElement)
  return lineElement
}

@ContainerElementMarker
fun ContainerElement.vStack(init: VerticalStackElement.() -> Unit = {}): VerticalStackElement {
  val element = VerticalStackElement(this)
  element.init()
  addContainerChild(element)
  return element
}

@ContainerElementMarker
fun ContainerElement.hStack(init: HorisontalStackElement.() -> Unit = {}): HorisontalStackElement {
  val element = HorisontalStackElement(this)
  element.init()
  addContainerChild(element)
  return element
}
