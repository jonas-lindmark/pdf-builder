package io.github.jonaslindmark.pdfcompose.elements

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode.APPEND
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import io.github.jonaslindmark.pdfcompose.getPage
import io.github.jonaslindmark.pdfcompose.style.Alignment
import io.github.jonaslindmark.pdfcompose.transformY
import java.awt.image.BufferedImage

class ImageElement(
    parent: Element,
    private val imagePath: String = "",
    private val bufferedImage: BufferedImage? = null
) : Element(parent), ContainerChild {
    override fun toElement() = this

    var imgHeight: Int? = null
    var imgWidth: Int? = null

    override fun instanceHeight(width: Float, startY: Float): Float {
        return imgHeight?.toFloat() ?: 0F
    }

    override fun renderInstance(
        pdDocument: PDDocument,
        startX: Float,
        endX: Float,
        startY: Float,
        minHeight: Float
    ) {

        val pdImage =
            if (bufferedImage != null) LosslessFactory.createFromImage(pdDocument, bufferedImage)
            else PDImageXObject.createFromFile(imagePath, pdDocument)

        imgHeight = imgHeight ?: pdImage.height
        imgWidth = imgWidth ?: pdImage.width

        val pdPage = getPage(document, pdDocument, startY + imgHeight!!)

        val realStartX =
            when (inheritedHorizontalAlignment) {
                Alignment.LEFT -> startX
                Alignment.RIGHT -> endX - imgWidth!!
                Alignment.CENTER -> startX + (endX - startX - imgWidth!!) / 2f
            }
        val transformedY = transformY(document, startY) - imgHeight!!
        PDPageContentStream(pdDocument, pdPage, APPEND, true).use { stream ->
            stream.drawImage(
                pdImage, realStartX, transformedY, imgWidth!!.toFloat(), imgHeight!!.toFloat()
            )
        }
    }
}
