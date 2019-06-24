package com.base.sdk.rich.editor.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.Spanned

/**
 * author:zack
 * Date:2019/5/30
 * Description:null
 */
class TextParagraphPointSpan(var number:Int=1) : NumberLeadingMarginSpan,IParagraphSpan<TextParagraphPointSpan> {

  private val mLeadingMargin = 30

  override fun drawLeadingMargin(canvas: Canvas,paint: Paint,x: Int,dir: Int,top: Int,baseline: Int,bottom: Int,
    text: CharSequence,start: Int,end: Int,first: Boolean,layout: Layout) {
    if ((text as Spanned).getSpanStart(this) == start) {
      val style = paint.style
      paint.style = Paint.Style.FILL
      canvas.drawText("\u2022", (x + dir).toFloat(), baseline.toFloat(), paint)
      paint.style = style
    }
  }

  override fun getLeadingMargin(first: Boolean): Int = mLeadingMargin + 50

  override fun newSpan(param: Any?): TextParagraphPointSpan = TextParagraphPointSpan(number+1)

  override fun newCopy(): TextParagraphPointSpan = TextParagraphPointSpan(number)

  override fun setLineNumber(number: Int) {
    this.number = number
  }

  override fun getLineNumber(): Int = number

}