package se.denacode.pdfcompose.elements

import se.denacode.pdfcompose.document
import se.denacode.pdfcompose.vStack
import java.io.ByteArrayOutputStream
import kotlin.test.Test

class VerticalStackElementSpec {
    @Test
    fun `Should create document with empty table`() {
        val pdDocument = document { vStack {} }

        // then: should save pdf
        ByteArrayOutputStream().use { os -> pdDocument.save(os) }
    }
}
