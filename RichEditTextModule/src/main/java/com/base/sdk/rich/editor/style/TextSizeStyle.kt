package com.base.sdk.rich.editor.style

import com.base.sdk.rich.editor.span.TextSizeSpan

/**
 * author:zack
 * Date:2019/5/28
 * Description:文字大小样式
 */
class TextSizeStyle : BaseStyle<TextSizeSpan>() {

  override fun getSpanClass(): Class<TextSizeSpan> = TextSizeSpan::class.java

  override fun newSpan(param:Any?): TextSizeSpan {
    if (param == null || param !is Float)
      return TextSizeSpan()
    return TextSizeSpan(param)
  }

}