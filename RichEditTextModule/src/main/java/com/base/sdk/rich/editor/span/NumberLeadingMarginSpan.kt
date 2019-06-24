package com.base.sdk.rich.editor.span

import android.text.style.LeadingMarginSpan

/**
 * author:zack
 * Date:2019/5/30
 * Description:段落包含行号的处理
 */
interface NumberLeadingMarginSpan : LeadingMarginSpan {

  /**
   * 设置段落number
   */
  fun setLineNumber(number:Int)

  /**
   * 获取段落number
   */
  fun getLineNumber():Int

}