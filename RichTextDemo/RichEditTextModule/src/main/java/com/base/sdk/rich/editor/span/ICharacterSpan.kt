package com.base.sdk.rich.editor.span

/**
 * author:zack
 * Date:2019/5/31
 * Description:样式的接口
 */
interface ICharacterSpan<E> : ISpan<E> {

  /**
   * 拷贝当前样式
   */
  fun newCopy():E

}