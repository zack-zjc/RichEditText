package com.base.sdk.rich.editor.style

import com.base.sdk.rich.editor.span.TextColorSpan

/**
 * author:zack
 * Date:2019/5/28
 * Description:文字颜色样式
 */
class TextColorStyle : BaseStyle<TextColorSpan>() {

  override fun getSpanClass(): Class<TextColorSpan> = TextColorSpan::class.java

  override fun newSpan(param: Any?): TextColorSpan {
    if (param == null || param !is Int)
      return TextColorSpan()
    return TextColorSpan(param)
  }

}