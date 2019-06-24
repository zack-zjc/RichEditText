package com.base.sdk.rich.editor.style

import android.text.Editable
import com.base.sdk.rich.editor.span.IReplacementSpan

/**
 * author:zack
 * Date:2019/5/31
 * Description:插入式样式
 */
interface IReplacementStyle<SPAN: IReplacementSpan<SPAN>> :IStyle<SPAN>{

  /**
   * 插入样式到一个段落中
   * 处理与上下文有关的时候使用
   */
  fun insertApplySpan(editable: Editable,lineStart:Int,lineEnd:Int,param: Any?)

}