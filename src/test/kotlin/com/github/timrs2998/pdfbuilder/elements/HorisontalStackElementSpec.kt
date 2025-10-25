package com.github.timrs2998.pdfbuilder.elements

import com.github.timrs2998.pdfbuilder.document
import com.github.timrs2998.pdfbuilder.hStack
import com.github.timrs2998.pdfbuilder.vStack
import io.kotest.core.spec.style.FunSpec
import java.io.ByteArrayOutputStream

class HorisontalStackElementSpec :
    FunSpec({
      test("document with empty row in table") {
        val pdDocument = document { vStack { hStack {} } }

        // then: should save pdf
        ByteArrayOutputStream().use { os -> pdDocument.save(os) }
      }
    })
