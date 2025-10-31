package io.github.jonaslindmark.pdfcompose.elements

import io.github.jonaslindmark.pdfcompose.document
import io.github.jonaslindmark.pdfcompose.vStack
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
