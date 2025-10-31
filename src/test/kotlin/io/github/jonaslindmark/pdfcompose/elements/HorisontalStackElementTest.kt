package io.github.jonaslindmark.pdfcompose.elements

import io.github.jonaslindmark.pdfcompose.document
import io.github.jonaslindmark.pdfcompose.hStack
import io.github.jonaslindmark.pdfcompose.vStack
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
