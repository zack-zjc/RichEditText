package com.base.sdk.rich.editor.style

import android.text.Editable
import android.text.Spannable
import com.base.sdk.rich.editor.span.IParagraphSpan
import com.base.sdk.rich.editor.span.NumberLeadingMarginSpan

/**
 * author:zack
 * Date:2019/5/30
 * Description:处理包含行号的样式处理
 */
abstract class BaseParagraphNumberStyle<SPAN: IParagraphSpan<SPAN>> : BaseStyle<SPAN>() {

  override fun canProcessSelectionText(): Boolean = false

  /**
   * 读取上下文的样式number使用
   */
  override fun insertApplySpan(editable: Editable,lineStart: Int,lineEnd: Int,param: Any?) {
    var currentNumber = 1
    if (NumberLeadingMarginSpan::class.java.isAssignableFrom(getSpanClass())){
      val currentSpanList = editable.getSpans(lineStart,lineEnd,NumberLeadingMarginSpan::class.java)
      if (currentSpanList != null && currentSpanList.isNotEmpty()){ //先查看当前是否存在行号并删除当前样式
        for (currentSpan in currentSpanList){
          editable.removeSpan(currentSpan)
        }
        val lineSpan = currentSpanList[currentSpanList.size - 1]
        currentNumber = lineSpan.getLineNumber()
      }else{
        val preNumberSpanList = editable.getSpans(lineStart - 2, lineStart - 1, NumberLeadingMarginSpan::class.java)
        if (preNumberSpanList != null && preNumberSpanList.isNotEmpty()){
          val previousSpan = preNumberSpanList[preNumberSpanList.size - 1]
          currentNumber = previousSpan.getLineNumber()+1
        }
      }
    }
    val newSpan = newSpan(currentNumber)
    editable.setSpan(newSpan,lineStart,lineEnd, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    resetPreviousNumberSpan(lineStart,editable)
    resetFollowingNumberSpan(lineEnd,editable,currentNumber)
  }

  /**
   * 删除样式并重置number
   */
  override fun deleteApplySpan(editable: Editable,lineStart: Int,lineEnd: Int) {
    super.deleteApplySpan(editable, lineStart, lineEnd)
    resetFollowingNumberSpan(lineEnd,editable,0)
  }

  /**
   * 当前样式包含行号时处理
   */
  override fun insertInputSpan(editable: Editable,lineStart: Int,lineEnd: Int,previousSpan: Any?) {
    if (previousSpan is NumberLeadingMarginSpan){
      val newSpan = newSpan(previousSpan.getLineNumber()+1)
      editable.setSpan(newSpan, lineStart, lineEnd, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
      resetFollowingNumberSpan(lineEnd,editable,previousSpan.getLineNumber()+1)
    }else{
      super.insertInputSpan(editable, lineStart, lineEnd, previousSpan)
    }
  }

  /**
   * 重置后面的span的index
   */
  private fun resetFollowingNumberSpan(preLineEnd:Int, editable:Editable, startNumber:Int) {
    var currentNumber = startNumber
    if (NumberLeadingMarginSpan::class.java.isAssignableFrom(getSpanClass())){
      val numberSpanList  = editable.getSpans(preLineEnd+1, preLineEnd + 2, NumberLeadingMarginSpan::class.java)
      if (null != numberSpanList && numberSpanList.isNotEmpty()){
        val total = numberSpanList.size
        var index = 0
        for (numberSpan in numberSpanList){
          val numberSpanStart = editable.getSpanStart(numberSpan)
          val numberSpanEnd = editable.getSpanEnd(numberSpan)
          val newNumber = ++currentNumber
          val newSpan = newSpan(newNumber)
          editable.removeSpan(numberSpan)
          editable.setSpan(newSpan,numberSpanStart,numberSpanEnd, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
          index++
          if (total == index){
            resetFollowingNumberSpan(numberSpanEnd, editable, newNumber)
          }
        }
      }
    }
  }

  /**
   * 重置前面的span的index和样式
   */
  private fun resetPreviousNumberSpan(preLineStart:Int, editable:Editable) {
    if (NumberLeadingMarginSpan::class.java.isAssignableFrom(getSpanClass())){
      val numberSpanList  = editable.getSpans(preLineStart - 2, preLineStart - 1, NumberLeadingMarginSpan::class.java)
      if (null != numberSpanList && numberSpanList.isNotEmpty()){
        val total = numberSpanList.size
        var index = 0
        for (numberSpan in numberSpanList.reversedArray()){
          val numberSpanStart = editable.getSpanStart(numberSpan)
          val numberSpanEnd = editable.getSpanEnd(numberSpan)
          val newSpan = newSpan(numberSpan.getLineNumber())
          editable.removeSpan(numberSpan)
          editable.setSpan(newSpan,numberSpanStart,numberSpanEnd, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
          index++
          if (total == index){
            resetPreviousNumberSpan(numberSpanStart, editable)
          }
        }
      }
    }
  }

}