package com.base.sdk.rich.editor.widget.callback

/**
 * author:zack
 * Date:2019/5/30
 * Description:富文本文字选中监听器
 */
interface SelectionChangeListener {

  fun onSelectionChanged(selectStart: Int,selectEnd: Int)

}