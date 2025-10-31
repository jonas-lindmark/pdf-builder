package se.denacode.pdfcompose.elements

import io.kotest.core.spec.style.FunSpec
import java.io.ByteArrayOutputStream
import se.denacode.pdfcompose.document
import se.denacode.pdfcompose.hStack
import se.denacode.pdfcompose.vStack

class HorisontalStackElementSpec :
    FunSpec({
      test("document with empty row in table") {
        val pdDocument = document { vStack { hStack {} } }

        // then: should save pdf
        ByteArrayOutputStream().use { os -> pdDocument.save(os) }
      }
    })
