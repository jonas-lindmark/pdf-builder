package se.denacode.pdfcompose

import java.awt.RenderingHints
import java.awt.Transparency
import java.awt.image.BufferedImage
import kotlin.math.ceil
import kotlin.math.roundToInt

fun BufferedImage.resize(width: Int, height: Int): BufferedImage {
    var targetWidth = width
    var targetHeight = height
    val ratio = (this.height.toFloat() / this.width.toFloat())
    if (ratio <= 1) { // square or landscape-oriented image
        targetWidth = (targetHeight.toFloat() / ratio).roundToInt()
    } else { // portrait image
        targetHeight = ceil((targetWidth.toFloat() * ratio).toDouble()).toInt()
    }
    val isOpaque = this.transparency == Transparency.OPAQUE
    val bi =
        BufferedImage(
            targetWidth,
            targetHeight,
            if (isOpaque) BufferedImage.TYPE_INT_RGB else BufferedImage.TYPE_INT_ARGB
        )
    val g2d = bi.createGraphics()
    g2d.setRenderingHint(
        RenderingHints.KEY_INTERPOLATION,
        RenderingHints
            .VALUE_INTERPOLATION_BILINEAR
    ) // produces a balanced resizing (fast and decent quality)
    g2d.drawImage(this, 0, 0, targetWidth, targetHeight, null)
    g2d.dispose()
    return bi
}
