package se.denacode.pdfcompose.elements

import io.kotest.core.spec.style.FunSpec
import java.io.ByteArrayOutputStream
import se.denacode.pdfcompose.document
import se.denacode.pdfcompose.vStack

class VerticalStackElementSpec :
    FunSpec({
      test("document with empty table") {
        val pdDocument = document { vStack {} }

        // then: should save pdf
        ByteArrayOutputStream().use { os -> pdDocument.save(os) }
      }
    })
