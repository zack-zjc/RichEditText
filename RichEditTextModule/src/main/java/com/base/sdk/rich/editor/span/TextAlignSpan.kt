package com.base.sdk.rich.editor.span

import android.text.Layout.Alignment
import android.text.style.AlignmentSpan

/**
 * author:zack
 * Date:2019/5/28
 * Description:文字段落居中等样式
 */
class TextAlignSpan(var textAlignment: Alignment = Alignment.ALIGN_LEFT) : AlignmentSpan,IParagraphSpan<TextAlignSpan> {

  override fun newSpan(param: Any?): TextAlignSpan = TextAlignSpan(param as Alignment)

  override fun newCopy(): TextAlignSpan = TextAlignSpan(textAlignment)

  override fun getAlignment(): Alignment = textAlignment

}