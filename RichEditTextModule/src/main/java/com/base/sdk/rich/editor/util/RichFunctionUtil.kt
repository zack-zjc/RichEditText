package com.base.sdk.rich.editor.util

import android.text.Selection
import android.widget.EditText

/**
 * author:zack
 * Date:2019/5/28
 * Description:富文本的方法
 */
object RichFunctionUtil {

  /**
   * 获取当前所在第几行
   */
  fun getCurrentCursorLine(editText: EditText): Int {
    val selectionStart = Selection.getSelectionStart(editText.text)
    val layout = editText.layout ?: return -1
    return if (selectionStart != -1) {
      layout.getLineForOffset(selectionStart)
    } else -1
  }

  /**
   * 获取当前行所在起始位置
   */
  fun getThisLineStart(editText: EditText,line: Int): Int {
    var currentLine = line
    val layout = editText.layout
    var start = 0
    if (currentLine > 0) {
      start = layout.getLineStart(currentLine)
      if (start > 0) {
        val text = editText.text.toString()
        var lastChar = text[start - 1]
        while (lastChar != '\n') {
          if (currentLine > 0) {
            currentLine--
            start = layout.getLineStart(currentLine)
            if (start > 1) {
              start--
              lastChar = text[start]
            } else {
              break
            }
          }
        }
      }
    }
    return start
  }

  /**
   * 获取当前行结束位置
   */
  fun getThisLineEnd(editText: EditText,currentLine: Int): Int {
    val layout = editText.layout
    return if (-1 != currentLine) {
      layout.getLineEnd(currentLine)
    } else -1
  }

}