package com.base.sdk.rich.editor.span

import android.text.TextPaint
import android.text.style.CharacterStyle

/**
 * author:zack
 * Date:2019/5/28
 * Description:文字下划线
 */
class TextUnderLineSpan :CharacterStyle(),ICharacterSpan<TextUnderLineSpan> {

  override fun newSpan(param: Any?): TextUnderLineSpan = TextUnderLineSpan()

  override fun newCopy(): TextUnderLineSpan = TextUnderLineSpan()

  override fun updateDrawState(textPaint: TextPaint?) {
    textPaint?.isUnderlineText = true
  }

}