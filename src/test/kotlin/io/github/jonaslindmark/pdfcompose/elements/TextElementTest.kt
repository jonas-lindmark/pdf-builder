package io.github.jonaslindmark.pdfcompose.elements

import org.junit.jupiter.api.BeforeEach
import io.github.jonaslindmark.pdfcompose.document
import io.github.jonaslindmark.pdfcompose.style.Wrap
import io.github.jonaslindmark.pdfcompose.text
import java.io.ByteArrayOutputStream
import kotlin.test.Test

class TextElementTest {

    private lateinit var wrapNoneDocument: Document
    private lateinit var wrapCharacterDocument: Document

    @BeforeEach
    fun setup() {
        wrapCharacterDocument = Document()
        wrapCharacterDocument.wrap = Wrap.CHARACTER
        wrapNoneDocument = Document()
        wrapNoneDocument.wrap = Wrap.NONE
    }


    @Test
    fun `Should create document with text element`() {
        val pdDocument = document { text("Hello, world!") }

        // then: should save pdf
        ByteArrayOutputStream().use { os -> pdDocument.save(os) }
    }

    @Test
    fun `Should generate word boundaries`() {
        // then: should wrap text on word boundaries on Wrap.WORD
        assert(TextElement(Document(), "Hello, world!").wrapText(100f) == listOf("Hello, world!"))
        assert(TextElement(Document(), "Hello, world!").wrapText(50f) == listOf("Hello,", "world!"))
        assert(TextElement(Document(), "Hello,world!").wrapText(50f) == emptyList<String>())

        // then should wrap text on word boundaries still normally on Wrap.CHARACTER
        assert(
            TextElement(wrapCharacterDocument, "Hello, world!").wrapText(100f) ==
                    listOf("Hello, world!")
        )
        assert(
            TextElement(wrapCharacterDocument, "Hello, world!").wrapText(50f) ==
                    listOf("Hello,", "world!")
        )
        // then: should wrap text on word boundaries and character boundaries on Wrap.CHARACTER
        assert(
            TextElement(wrapCharacterDocument, "Hello,world!").wrapText(50f) ==
                    listOf("Hello,", "world!")
        )
        assert(
            TextElement(
                wrapCharacterDocument,
                "Hello, world! Hello, world! HelloHello HelloHello HelloHelloHello TestTestTestTestTestTestTest Hello HelloWorld Hello"
            )
                .wrapText(100f) ==
                    listOf(
                        "Hello, world! Hello,",
                        "world! HelloHello",
                        "HelloHello",
                        "HelloHelloHello",
                        "TestTestTestTe",
                        "stTestTestTest Hello",
                        "HelloWorld Hello"
                    )
        )
        val testString = "Hello".repeat(200)
        val textElements = TextElement(wrapCharacterDocument, testString).wrapText(100f)
        assert(textElements.size == 64)
        assert(textElements.joinToString("").contentEquals(testString))
    }

    @Test
    fun `Should not wrap text on NONE`() {
        assert(
            TextElement(wrapNoneDocument, "Hello, world!").wrapText(100f) ==
                    listOf("Hello, world!")
        )
        assert(
            TextElement(wrapNoneDocument, "Hello, world!").wrapText(50f) == listOf("Hello, world!")
        )
        assert(
            TextElement(wrapNoneDocument, "Hello,world!").wrapText(50f) == listOf("Hello,world!")
        )
    }
}

