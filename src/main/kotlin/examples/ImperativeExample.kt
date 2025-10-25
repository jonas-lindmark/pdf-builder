package examples

import com.github.timrs2998.pdfbuilder.elements.Document
import com.github.timrs2998.pdfbuilder.elements.HorisontalStackElement
import com.github.timrs2998.pdfbuilder.elements.TextElement
import com.github.timrs2998.pdfbuilder.elements.VerticalStackElement
import com.github.timrs2998.pdfbuilder.style.Alignment
import com.github.timrs2998.pdfbuilder.style.Border
import java.awt.Color

/**
 * Class containing main method demonstrate creation of a sample "output.pdf" in an imperative, Java
 * style.
 */
object ImperativeExample {

  @JvmStatic
  fun main(args: Array<String>) {
    val document = Document()

    val initialText = TextElement(document, "Hello World!")
    initialText.fontColor = Color(1f, .1f, .1f)
    initialText.backgroundColor = Color(222, 222, 222)
    document.addContainerChild(initialText)

    document.addContainerChild(
        TextElement(
            document,
            "WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. "))

    document.addContainerChild(TextElement(document, "This is another line"))
    document.addContainerChild(TextElement(document, "This is a third line"))
    document.addContainerChild(TextElement(document, "This is a fourth line"))

    IntRange(0, 59).forEach { i -> document.addContainerChild(TextElement(document, "Value: $i")) }

    // Table demo with stacks
    val table = VerticalStackElement(document)
    IntRange(0, 4).forEach { i ->
      val row = HorisontalStackElement(table)
      row.addColumn(TextElement(row, "r$i c1"))
      row.addColumn(TextElement(row, "r$i c2"))
      row.addColumn(TextElement(row, "r$i c3"))

      table.addContainerChild(row)
    }
    (table.children[2] as HorisontalStackElement).children[1].border =
        Border(1f, 1f, 1f, 1f, Color.RED)
    (table.children[2] as HorisontalStackElement).children[1].horizontalAlignment = Alignment.RIGHT
    (table.children[3] as HorisontalStackElement).horizontalAlignment = Alignment.CENTER
    document.addContainerChild(table)

    table.border = Border(1f, 2f, 3f, 4f, Color.GREEN, Color.RED, Color.BLUE, Color.BLACK)
    document.render().use { pdDocument -> pdDocument.save("output.pdf") }
  }
}
