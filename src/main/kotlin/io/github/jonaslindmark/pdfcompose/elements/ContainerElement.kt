package io.github.jonaslindmark.pdfcompose.elements

abstract class ContainerElement(parent: Element?) : Element(parent) {
    val children: List<Element>
        get() = containerChildren.map { it.toElement() }

    protected val containerChildren = mutableListOf<ContainerChild>()

    fun addContainerChild(child: ContainerChild) {
        containerChildren.add(child)
    }
}
