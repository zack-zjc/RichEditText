package com.base.sdk.rich.editor.style

import android.text.Editable
import android.text.Spannable
import com.base.sdk.rich.editor.span.IUnionTextSpan

/**
 * author:zack
 * Date:2019/5/31
 * Description:处理一段文字的样式
 */
abstract class BaseUnionTextStyle<SPAN: IUnionTextSpan<SPAN>> :IUnionTextStyle<SPAN> {

  override fun insertApplySpan(editable: Editable,lineStart: Int,lineEnd: Int,param: Any?) {
    val span = newSpan(param)
    editable.setSpan(span,lineStart,lineEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
  }

  override fun canProcessSelectionText(): Boolean = false

  override fun canProcessParagraphStyle(): Boolean = false

}