package com.base.sdk.rich.editor.style

import android.text.Layout.Alignment
import com.base.sdk.rich.editor.span.TextAlignSpan

/**
 * author:zack
 * Date:2019/5/28
 * Description:文字居中样式
 */
class TextAlignStyle : BaseStyle<TextAlignSpan>() {

  override fun getSpanClass(): Class<TextAlignSpan> = TextAlignSpan::class.java

  override fun newSpan(param: Any?): TextAlignSpan {
    if (param == null || param !is Alignment)
      return TextAlignSpan()
    return TextAlignSpan(param)
  }

}