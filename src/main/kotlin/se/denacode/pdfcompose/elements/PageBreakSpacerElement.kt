package se.denacode.pdfcompose.elements

import org.apache.pdfbox.pdmodel.PDDocument

// we want to fill the page but not go over so an extra blank page is inserted
private const val MARGIN_TO_EDGE = 0.5F

/** Fill up remaining space on page */
class PageBreakSpacerElement(parent: Element?) : Element(parent), ContainerChild {
  override fun toElement() = this

  override fun instanceHeight(width: Float, startY: Float): Float {
    val pageLocalStartY = startY % document.pageHeight
    return document.pageHeight -
        pageLocalStartY -
        document.margin.bottom -
        document.padding.bottom -
        MARGIN_TO_EDGE
  }

  override fun renderInstance(
      pdDocument: PDDocument,
      startX: Float,
      endX: Float,
      startY: Float,
      minHeight: Float
  ) {}
}
