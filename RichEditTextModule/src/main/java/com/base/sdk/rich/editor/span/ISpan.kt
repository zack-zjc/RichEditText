package com.base.sdk.rich.editor.span

/**
 * author:zack
 * Date:2019/5/30
 * Description:包含参数可修改的span
 */
interface ISpan<E>{

  /**
   * 创建一个新的样式
   */
  fun newSpan(param:Any?):E

}