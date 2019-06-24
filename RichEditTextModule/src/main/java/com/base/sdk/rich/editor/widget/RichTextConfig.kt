package com.base.sdk.rich.editor.widget

import com.base.sdk.rich.editor.style.IStyle
import com.base.sdk.rich.editor.style.TextBoldStyle
import com.base.sdk.rich.editor.style.TextColorStyle
import com.base.sdk.rich.editor.style.TextLinkStyle
import com.base.sdk.rich.editor.style.TextSizeStyle
import com.base.sdk.rich.editor.style.TextSkewStyle
import com.base.sdk.rich.editor.style.TextUnderLineStyle

/**
 * author:zack
 * Date:2019/6/24
 * Description:当前富文本的配置
 */
object RichTextConfig {

  //当前支持的所有样式，默认支持几种样式
  val mStylesList : ArrayList<IStyle<*>> = arrayListOf(TextBoldStyle(), TextSkewStyle(), TextUnderLineStyle(),
      TextSizeStyle(), TextColorStyle(),TextLinkStyle())

}