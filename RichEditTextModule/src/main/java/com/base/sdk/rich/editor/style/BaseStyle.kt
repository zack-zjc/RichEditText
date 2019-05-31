package com.base.sdk.rich.editor.style

import android.text.Editable
import android.text.Spannable
import com.base.sdk.rich.editor.span.ICharacterSpan

/**
 * author:zack
 * Date:2019/5/30
 * Description:实现基类的样式
 */
abstract class BaseStyle<SPAN: ICharacterSpan<SPAN>> : IBaseStyle<SPAN>{

  override fun canProcessSelectionText(): Boolean = true

  override fun canProcessParagraphStyle(): Boolean = true

  /**
   * 普通与上下文样式无关的直接继承就行
   */
  override fun insertApplySpan(editable: Editable,lineStart: Int,lineEnd: Int,param: Any?) {
    val span = newSpan(param)
    editable.setSpan(span,lineStart,lineEnd, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
  }

  /**
   * 普通与上下文无关的样式直接删除即可
   */
  override fun deleteApplySpan(editable: Editable,lineStart: Int,lineEnd: Int) {
    val spans = editable.getSpans(lineStart, lineEnd, getSpanClass())
    if (spans != null && spans.isNotEmpty()){
      for (span in spans){
        editable.removeSpan(span)
      }
    }
  }

  /**
   * 插入当前样式该样式与上下文无关
   */
  override fun insertInputSpan(editable: Editable,lineStart: Int,lineEnd: Int,previousSpan: Any?) {
    editable.setSpan(previousSpan, lineStart, lineEnd, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
  }

}