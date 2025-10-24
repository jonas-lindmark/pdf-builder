package com.github.timrs2998.pdfbuilder

import com.github.timrs2998.pdfbuilder.style.Alignment
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitArray
import com.google.zxing.qrcode.QRCodeWriter
import java.awt.Color
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode.APPEND

class QrCodeElement(
    parent: Element,
    val content: String,
) : Element(parent), ContainerChild {
  override fun toElement() = this

  var height: Float = 50F
  var width: Float = 50F

  override fun instanceHeight(width: Float, startY: Float): Float {
    return height
  }

  override fun renderInstance(
      pdDocument: PDDocument,
      startX: Float,
      endX: Float,
      startY: Float,
      minHeight: Float
  ) {

    val pdPage = getPage(document, pdDocument, startY + height)

    val realStartX =
        when (inheritedHorizontalAlignment) {
          Alignment.LEFT -> startX
          Alignment.RIGHT -> endX - width
          Alignment.CENTER -> startX + (endX - startX - width) / 2f
        }
    val transformedY = transformY(document, startY) - height

    val qrCodeWriter = QRCodeWriter()
    val bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 160, 160)
    val qrPixelWidth = width / bitMatrix.width
    val qrPixelHeight = height / bitMatrix.height

    PDPageContentStream(pdDocument, pdPage, APPEND, true).use { stream ->
      stream.setNonStrokingColor(Color.BLACK)
      stream.setLineWidth(0F)
      var row = BitArray(bitMatrix.width)
      for (y in 0 until bitMatrix.height) {
        row = bitMatrix.getRow(y, row)
        for (x in 0 until bitMatrix.width) {
          if (row.get(x)) {
            stream.addRect(
                realStartX + x * qrPixelWidth,
                transformedY + y * qrPixelWidth,
                qrPixelWidth,
                qrPixelHeight)
            stream.fill()
          }
        }
      }

      // stream.drawImage(
      //  pdImage, realStartX, transformedY, imgWidth!!.toFloat(), imgHeight!!.toFloat())
    }
  }
}
