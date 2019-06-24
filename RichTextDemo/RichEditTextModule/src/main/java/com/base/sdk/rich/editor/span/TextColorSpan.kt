package com.base.sdk.rich.editor.span

import android.graphics.Color
import android.text.TextPaint
import android.text.style.CharacterStyle

/**
 * author:zack
 * Date:2019/5/28
 * Description:文字颜色
 */
class TextColorSpan(var color:Int=Color.BLACK) : CharacterStyle(),ICharacterSpan<TextColorSpan> {

  override fun newSpan(param: Any?): TextColorSpan = TextColorSpan(param as Int)

  override fun newCopy(): TextColorSpan = TextColorSpan(color)

  override fun updateDrawState(textPaint: TextPaint?) {
    textPaint?.color = color
  }

}