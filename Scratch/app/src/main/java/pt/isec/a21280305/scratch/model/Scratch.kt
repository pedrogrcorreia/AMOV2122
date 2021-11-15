package pt.isec.a21280305.scratch.model

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Path

data class Scratch(
    var title: String? = null,
    val lines: ArrayList<Line> = arrayListOf(Line(Path(), Color.BLACK)),
    val bkgColor: Int = Color.WHITE,
    val bkgImage: String? = null, // image from gallery or taken
    var preview: Bitmap? = null // history
){
    fun addLine(colorLine: Int = Color.BLACK){
        lines.add(Line(Path(), colorLine))
    }
}