package com.base.sdk.rich.editor.span

import android.text.TextPaint
import android.text.style.CharacterStyle

/**
 * author:zack
 * Date:2019/5/28
 * Description:文字加粗span
 */
class TextBoldSpan : CharacterStyle(),ICharacterSpan<TextBoldSpan> {

  override fun newCopy(): TextBoldSpan = TextBoldSpan()

  override fun newSpan(param: Any?): TextBoldSpan = TextBoldSpan()

  override fun updateDrawState(textPaint: TextPaint?) {
    textPaint?.isFakeBoldText = true
  }

}