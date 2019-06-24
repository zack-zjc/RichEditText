package com.base.sdk.rich.editor.style

import android.text.Editable
import com.base.sdk.rich.editor.span.IUnionTextSpan

/**
 * author:zack
 * Date:2019/5/31
 * Description:一段整体文字的样式
 * param都是pair对象
 */
interface IUnionTextStyle<SPAN: IUnionTextSpan<SPAN>> :IStyle<SPAN>{

  /**
   * 插入样式到一个段落中
   */
  fun insertApplySpan(editable: Editable,lineStart:Int,lineEnd:Int,param: Any?)

}