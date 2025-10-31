package se.denacode.pdfcompose.elements

import se.denacode.pdfcompose.document
import se.denacode.pdfcompose.hStack
import se.denacode.pdfcompose.vStack
import java.io.ByteArrayOutputStream
import kotlin.test.Test

class HorisontalStackElementTest {
  @Test
  fun `Should create document with empty row`() {
    val pdDocument = document { vStack { hStack {} } }

    // then: should save pdf
    ByteArrayOutputStream().use { os -> pdDocument.save(os) }
  }
}
