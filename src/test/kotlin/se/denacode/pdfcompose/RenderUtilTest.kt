package se.denacode.pdfcompose

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class RenderUtilTest {

    @Test
    fun `Should compute page indexes correctly`() {
        // expect get index of page #y / #pageHeight
        assertEquals(getPageIndex(100f, 0f), 0)
        assertEquals(getPageIndex(100f, 99f), 0)
        assertEquals(getPageIndex(100f, 100f), 1)
        assertEquals(getPageIndex(100f, 101f), 1)
        assertEquals(getPageIndex(100f, 199f), 1)
        assertEquals(getPageIndex(100f, 200f), 2)
        assertEquals(getPageIndex(100f, 201f), 2)
    }
}
