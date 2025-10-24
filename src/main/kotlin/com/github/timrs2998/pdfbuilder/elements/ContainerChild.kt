package com.github.timrs2998.pdfbuilder.elements

/** Interface for elements that may be added to other elements (not all elements are). */
interface ContainerChild {
  /**
   * Get the actual element, typically just this if interface is implemented by the element itself
   */
  fun toElement(): Element
}
