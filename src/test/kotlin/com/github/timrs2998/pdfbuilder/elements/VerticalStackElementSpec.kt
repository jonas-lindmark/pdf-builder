package com.github.timrs2998.pdfbuilder.elements

import com.github.timrs2998.pdfbuilder.document
import com.github.timrs2998.pdfbuilder.vStack
import io.kotest.core.spec.style.FunSpec
import java.io.ByteArrayOutputStream

class VerticalStackElementSpec :
    FunSpec({
      test("document with empty table") {
        val pdDocument = document { vStack {} }

        // then: should save pdf
        ByteArrayOutputStream().use { os -> pdDocument.save(os) }
      }
    })
