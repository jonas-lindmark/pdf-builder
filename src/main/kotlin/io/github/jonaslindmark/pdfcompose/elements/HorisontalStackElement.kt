package io.github.jonaslindmark.pdfcompose.elements

import org.apache.pdfbox.pdmodel.PDDocument

class HorisontalStackElement(override val parent: ContainerElement) :
    ContainerElement(parent), ContainerChild {
    override fun toElement() = this

    val columns: List<Element>
        get() = children

    var minHeight = 0f

    var weights = mutableListOf<Float>()

    fun addColumn(column: ContainerChild) {
        containerChildren.add(column)
    }

    override fun instanceHeight(width: Float, startY: Float): Float {
        val heights = children.map { it.height(it.getWidth(width), startY) }
        return (heights.maxOrNull() ?: 0f).coerceAtLeast(minHeight)
    }

    override fun renderInstance(
        pdDocument: PDDocument,
        startX: Float,
        endX: Float,
        startY: Float,
        minHeight: Float
    ) {
        val height = height(endX - startX, startY, minHeight)
        children.fold(startX) { columnStartX, column ->
            val columnWidth = column.getWidth(endX - startX)
            column.render(pdDocument, columnStartX, columnStartX + columnWidth, startY, height)
            columnStartX + columnWidth
        }
    }

    private fun Element.getWidth(totalWidth: Float): Float {
        val multiplier =
            if (weights.isEmpty()) 1f / children.size
            else weights[children.indexOf(this)] / weights.sum()
        return totalWidth * multiplier
    }
}
