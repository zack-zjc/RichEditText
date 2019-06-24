package com.base.sdk.rich.editor.style

import com.base.sdk.rich.editor.span.TextParagraphPointSpan

/**
 * author:zack
 * Date:2019/5/30
 * Description:段落点处理
 */
class TextParagraphPointStyle : BaseParagraphNumberStyle<TextParagraphPointSpan>() {

  override fun getSpanClass(): Class<TextParagraphPointSpan> = TextParagraphPointSpan::class.java

  override fun newSpan(param: Any?): TextParagraphPointSpan {
    if (param == null || param !is Int)
      return TextParagraphPointSpan()
    return TextParagraphPointSpan(param)
  }

}