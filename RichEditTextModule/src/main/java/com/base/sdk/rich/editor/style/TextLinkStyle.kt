package com.base.sdk.rich.editor.style

import com.base.sdk.rich.editor.span.TextLinkSpan

/**
 * author:zack
 * Date:2019/5/31
 * Description:点击style
 */
class TextLinkStyle : BaseUnionTextStyle<TextLinkSpan>() {

  override fun getSpanClass(): Class<TextLinkSpan> = TextLinkSpan::class.java

  override fun newSpan(param: Any?): TextLinkSpan = TextLinkSpan(param)


}