package com.base.sdk.rich.editor.span

import android.text.TextPaint
import android.text.style.MetricAffectingSpan
import com.base.sdk.rich.editor.Constants

/**
 * author:zack
 * Date:2019/5/28
 * Description:文字大小
 */
class TextSizeSpan(var textSize :Float = Constants.DEFAULT_FONT_SIZE) : MetricAffectingSpan(),ICharacterSpan<TextSizeSpan> {

  override fun newSpan(param: Any?): TextSizeSpan = TextSizeSpan(param as Float)

  override fun newCopy(): TextSizeSpan = TextSizeSpan(textSize)

  override fun updateMeasureState(textPaint: TextPaint) {
    textPaint.textSize = textSize*textPaint.density
  }

  override fun updateDrawState(textPaint: TextPaint?) {
    textPaint?.let {
      it.textSize = textSize*it.density
    }
  }

}