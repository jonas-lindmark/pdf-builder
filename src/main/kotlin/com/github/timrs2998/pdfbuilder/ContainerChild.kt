package com.github.timrs2998.pdfbuilder

/** Interface for elements that may be added to other elements (not all elements are) */
interface ContainerChild {
  fun toElement(): Element
}
