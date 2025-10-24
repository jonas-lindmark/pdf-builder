package com.github.timrs2998.pdfbuilder

import java.awt.image.BufferedImage
import org.apache.pdfbox.pdmodel.PDDocument

/** A DSL for Kotlin, Groovy or Java 8 consumers of this API. */
@DslMarker annotation class DocumentMarker

/**
 * Creates the outermost [Document] [element][Element] representing the pdf, and returns the
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

@TableMarker
fun Document.table(init: TableElement.() -> Unit): TableElement {
  val tableElement = TableElement(this)
  tableElement.init()
  addContainerChild(tableElement)
  return tableElement
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

@DslMarker annotation class TableMarker

@TableMarker
fun TableElement.header(init: RowElement.() -> Unit): RowElement {
  val rowElement = RowElement(this)
  rowElement.init()
  this.header = rowElement
  return rowElement
}

@TableMarker
fun TableElement.row(init: RowElement.() -> Unit): RowElement {
  val rowElement = RowElement(this)
  rowElement.init()
  this.rows.add(rowElement)
  return rowElement
}

@DslMarker annotation class RowMarker
