package se.denacode.pdfcompose

import org.apache.pdfbox.pdmodel.PDDocument
import se.denacode.pdfcompose.elements.ContainerChild
import se.denacode.pdfcompose.elements.Element
import se.denacode.pdfcompose.style.Alignment
import java.awt.Color

enum class LineOrientation {
    VERTICAL,
    HORIZONTAL
}

class LineElement(
    parent: Element,
    val orientation: LineOrientation,
    val lineLength: Float,
    var lineWidth: Float
) : Element(parent), ContainerChild {
    override fun toElement() = this

    val height: Float
        get() =
            when (orientation) {
                LineOrientation.VERTICAL -> lineLength
                LineOrientation.HORIZONTAL -> lineWidth
            }

    val width: Float
        get() =
            when (orientation) {
                LineOrientation.VERTICAL -> lineWidth
                LineOrientation.HORIZONTAL -> lineLength
            }

    override fun instanceHeight(width: Float, startY: Float): Float {
        println("inatanceHeight: $height orient:$orientation")
        return height
    }

    override fun renderInstance(
        pdDocument: PDDocument,
        startX: Float,
        endX: Float,
        startY: Float,
        minHeight: Float
    ) {

        val realStartX =
            when (inheritedHorizontalAlignment) {
                Alignment.LEFT -> startX
                Alignment.RIGHT -> endX - width
                Alignment.CENTER -> startX + (endX - startX - width) / 2f
            }

        val (transformedEndX, transformedEndY) =
            when (orientation) {
                LineOrientation.HORIZONTAL -> Pair(realStartX + lineLength, startY)
                LineOrientation.VERTICAL -> Pair(realStartX, startY + lineLength)
            }
        drawLine(
            document,
            pdDocument,
            realStartX,
            startY,
            transformedEndX,
            transformedEndY,
            lineWidth,
            Color.BLACK
        )
    }
}
