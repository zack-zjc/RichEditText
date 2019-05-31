package com.base.sdk.rich.editor.style

import com.base.sdk.rich.editor.span.ISpan

/**
 * author:zack
 * Date:2019/5/28
 * Description:应用的style接口
 */
interface IStyle<SPAN: ISpan<SPAN>> {

  /**
   * 获取对应的样式类
   */
  fun getSpanClass():Class<SPAN>

  /**
   * 通过参数new出对应的span类
   */
  fun newSpan(param:Any?):SPAN

  /**
   * 是否可以处理选中一段文字的样式
   */
  fun canProcessSelectionText():Boolean

  /**
   * 是否可以处理段落的样式
   */
  fun canProcessParagraphStyle():Boolean

}