package com.base.sdk.rich.editor.span

import android.text.TextPaint
import android.text.style.CharacterStyle

/**
 * author:zack
 * Date:2019/5/28
 * Description:文字斜体
 */
class TextSkewSpan : CharacterStyle(),ICharacterSpan<TextSkewSpan> {

  override fun newSpan(param: Any?): TextSkewSpan = TextSkewSpan()

  override fun newCopy(): TextSkewSpan = TextSkewSpan()

  override fun updateDrawState(textPaint: TextPaint?) {
    textPaint?.textSkewX = -0.25F
  }

}