package io.github.jonaslindmark.pdfcompose.elements

import org.apache.pdfbox.pdmodel.PDDocument

class VerticalStackElement(parent: Element?) : ContainerElement(parent), ContainerChild {
    override fun toElement() = this

    override fun instanceHeight(width: Float, startY: Float): Float {
        return children.fold(0F) { acc, element -> acc + element.height(width, startY + acc) }
    }

    override fun renderInstance(
        pdDocument: PDDocument,
        startX: Float,
        endX: Float,
        startY: Float,
        minHeight: Float
    ) {
        // TODO handle pages?
        children.fold(startY) { acc, child ->
            child.render(pdDocument, startX, endX, acc)
            acc + child.height(endX - startX, acc)
        }
    }
}
