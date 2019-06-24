package com.base.sdk.rich.editor.style

import com.base.sdk.rich.editor.span.TextBoldSpan

/**
 * author:zack
 * Date:2019/5/28
 * Description:加粗样式
 */
class TextBoldStyle : BaseStyle<TextBoldSpan>() {

  override fun getSpanClass(): Class<TextBoldSpan> = TextBoldSpan::class.java

  override fun newSpan(param: Any?): TextBoldSpan = TextBoldSpan()

}