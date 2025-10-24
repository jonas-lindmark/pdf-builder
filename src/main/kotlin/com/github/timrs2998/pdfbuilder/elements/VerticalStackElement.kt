package com.github.timrs2998.pdfbuilder.elements

import org.apache.pdfbox.pdmodel.PDDocument

class VerticalStackElement(parent: Element?) : ContainerElement(parent), ContainerChild {
  override fun toElement() = this

  override fun instanceHeight(width: Float, startY: Float): Float {
    return children.fold(0F) { acc, element ->
      return acc + element.height(width, startY)
    }
  }

  override fun renderInstance(
      pdDocument: PDDocument,
      startX: Float,
      endX: Float,
      startY: Float,
      minHeight: Float
  ) {
    // TODO handle pages?
    children.fold(startY) { startY, child ->
      child.render(pdDocument, startX, endX, startY)
      startY + child.height(endX - startX, startY)
    }
  }
}
