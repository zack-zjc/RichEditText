package com.base.sdk.rich.editor.style

import com.base.sdk.rich.editor.span.TextUnderLineSpan

/**
 * author:zack
 * Date:2019/5/28
 * Description:文字下划线
 */
class TextUnderLineStyle : BaseStyle<TextUnderLineSpan>() {

  override fun getSpanClass(): Class<TextUnderLineSpan> = TextUnderLineSpan::class.java

  override fun newSpan(param:Any?): TextUnderLineSpan = TextUnderLineSpan()

}