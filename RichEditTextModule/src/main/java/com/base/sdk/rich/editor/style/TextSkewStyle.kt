package com.base.sdk.rich.editor.style

import com.base.sdk.rich.editor.span.TextSkewSpan

/**
 * author:zack
 * Date:2019/5/28
 * Description:文字斜体
 */
class TextSkewStyle : BaseStyle<TextSkewSpan>() {

  override fun getSpanClass(): Class<TextSkewSpan> = TextSkewSpan::class.java

  override fun newSpan(param:Any?): TextSkewSpan = TextSkewSpan()

}