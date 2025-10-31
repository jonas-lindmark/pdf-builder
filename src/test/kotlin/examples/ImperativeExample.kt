package examples

import io.kotest.core.spec.style.FunSpec
import java.awt.Color
import se.denacode.pdfcompose.elements.Document
import se.denacode.pdfcompose.elements.HorisontalStackElement
import se.denacode.pdfcompose.elements.TextElement
import se.denacode.pdfcompose.elements.VerticalStackElement
import se.denacode.pdfcompose.style.Alignment
import se.denacode.pdfcompose.style.Border
import se.denacode.pdfcompose.style.Margin

/**
 * Class containing main method demonstrate creation of a sample "output.pdf" in an imperative, Java
 * style.
 */
object ImperativeExample :
    FunSpec({ test("run ImperativeExample") { ImperativeExample.main(emptyArray()) } }) {
  @JvmStatic
  fun main(args: Array<String>) {
    val document = Document()

    val initialText = TextElement(document, "Hello World!")
    initialText.fontColor = Color(1f, .1f, .1f)
    initialText.backgroundColor = Color(222, 222, 222)
    document.addContainerChild(initialText)

    document.margin = Margin(8F, 8F, 8F, 8F)

    document.addContainerChild(
        TextElement(
            document,
            "WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. WRAP THIS TEXT. "))

    document.addContainerChild(TextElement(document, "This is another line"))
    document.addContainerChild(TextElement(document, "This is a third line"))
    document.addContainerChild(TextElement(document, "This is a fourth line"))

    IntRange(0, 40).forEach { i -> document.addContainerChild(TextElement(document, "Value: $i")) }

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
    document.render().use { pdDocument ->
      pdDocument.save("src/test/kotlin/examples/ImperativeExample.pdf")
    }
  }
}
