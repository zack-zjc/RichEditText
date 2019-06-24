package com.base.sdk.rich.editor.style

import com.base.sdk.rich.editor.span.TextParagraphNumberSpan

/**
 * author:zack
 * Date:2019/5/30
 * Description:段落数字
 */
class TextParagraphNumberStyle : BaseParagraphNumberStyle<TextParagraphNumberSpan>() {

  override fun getSpanClass(): Class<TextParagraphNumberSpan> = TextParagraphNumberSpan::class.java

  override fun newSpan(param: Any?): TextParagraphNumberSpan {
    if (param == null || param !is Int)
      return TextParagraphNumberSpan()
    return TextParagraphNumberSpan(param)
  }

}