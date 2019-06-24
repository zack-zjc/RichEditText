package com.base.sdk.rich.editor

import com.base.sdk.rich.editor.style.IStyle
import com.base.sdk.rich.editor.widget.RichTextConfig

/**
 * author:zack
 * Date:2019/6/24
 * Description:富文本的设置
 */
object RichTextLib {

  /**
   * 设置当前富文本支持的样式
   */
  fun setEnableStyle(styleList:ArrayList<out IStyle<*>>){
    RichTextConfig.mStylesList.clear()
    RichTextConfig.mStylesList.addAll(styleList)
  }

}