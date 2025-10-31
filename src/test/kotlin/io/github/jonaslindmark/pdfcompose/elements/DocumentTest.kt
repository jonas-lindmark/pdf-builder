package io.github.jonaslindmark.pdfcompose.elements

import org.apache.pdfbox.pdmodel.PDDocument
import java.io.ByteArrayOutputStream
import kotlin.test.Test

class DocumentTest() {

    @Test
    fun `Should create new document`() {
        val document = Document()

        assert(document.children.isEmpty())
        assert(document.backgroundColor == null)
        assert(document.fontColor == null)
        assert(document.fontSize == null)
        assert(document.horizontalAlignment == null)
        assert(document.fontName == null)
        document.inheritedBackgroundColor
        document.inheritedFontColor
        document.inheritedFontSize
        document.inheritedHorizontalAlignment
        document.inheritedPdFont
    }

    @Test
    fun `Should save empty document`() {
        val document = Document()
        lateinit var pdDocument: PDDocument

        // when render
        pdDocument = document.render()

        // then should save to pdf
        ByteArrayOutputStream().use { os -> pdDocument.save(os) }
    }
}
