package com.base.sdk.rich.editor.style

import android.text.Editable
import com.base.sdk.rich.editor.span.ICharacterSpan

/**
 * author:zack
 * Date:2019/5/30
 * Description:应用的style接口
 */
interface IBaseStyle<SPAN: ICharacterSpan<SPAN>> :IStyle<SPAN> {

  /**
   * 插入样式到一个段落中
   * 处理与上下文有关的时候使用
   * 默认可直接使用base实现
   */
  fun insertApplySpan(editable:Editable,lineStart:Int,lineEnd:Int,param: Any?)

  /**
   * 删除一个段落中的样式
   * 处理与上下文有关的时候使用
   * 默认可直接使用base实现
   */
  fun deleteApplySpan(editable:Editable,lineStart:Int,lineEnd:Int)

  /**
   * 用户输入触发插入样式的修改
   */
  fun insertInputSpan(editable:Editable,lineStart:Int,lineEnd:Int,previousSpan: Any?)

}