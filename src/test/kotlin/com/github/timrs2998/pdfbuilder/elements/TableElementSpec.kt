package com.github.timrs2998.pdfbuilder.elements

import com.github.timrs2998.pdfbuilder.document
import com.github.timrs2998.pdfbuilder.table
import io.kotest.core.spec.style.FunSpec
import java.io.ByteArrayOutputStream

class TableElementSpec :
    FunSpec({
      test("document with empty table") {
        val pdDocument = document { table {} }

        // then: should save pdf
        ByteArrayOutputStream().use { os -> pdDocument.save(os) }
      }
    })
