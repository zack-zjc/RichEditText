package com.base.sdk.rich.editor.util

import android.text.Editable
import android.text.Spannable
import android.text.Spanned
import android.widget.EditText
import com.base.sdk.rich.editor.Constants
import com.base.sdk.rich.editor.span.ICharacterSpan
import com.base.sdk.rich.editor.span.IUnionTextSpan
import com.base.sdk.rich.editor.style.IBaseStyle
import com.base.sdk.rich.editor.style.IReplacementStyle
import com.base.sdk.rich.editor.style.IStyle
import com.base.sdk.rich.editor.style.IUnionTextStyle

/**
 * author:zack
 * Date:2019/5/29
 * Description:样式适配的方法集合
 */
object RichStyleUtil {

  /**
   * 开始适配某个样式
   */
  fun <T : IStyle<*>> startApplyStyle(editText: EditText,style:T,apply: Boolean,param: Any? = null) {
    val editable = editText.editableText
    val currentLine = RichFunctionUtil.getCurrentCursorLine(editText)
    val lineStart = RichFunctionUtil.getThisLineStart(editText, currentLine)
    var lineEnd = RichFunctionUtil.getThisLineEnd(editText, currentLine)
    val selectionStart = editText.selectionStart
    val selectionEnd = editText.selectionEnd
    if (selectionStart == selectionEnd){ //当前段落修改样式
      if (!style.canProcessParagraphStyle()) return
      if (style is IBaseStyle<*>){
        style.deleteApplySpan(editable,lineStart,lineEnd)//取消这段样式
        if (apply){ //应用这段样式
          if (lineStart == lineEnd) { //当前是一个新的行时候插入一个空字符
            val previousSpanList = editable.getSpans(lineStart-2, lineStart-1, style.getSpanClass())
            if (previousSpanList != null && previousSpanList.isNotEmpty()){ //处理前一段落的样式缩进\n
              for (preSpan in previousSpanList){
                val preSpanStart = editable.getSpanStart(preSpan)
                val preSpanEnd = editable.getSpanEnd(preSpan)
                editable.removeSpan(preSpan)
                editable.setSpan(preSpan,preSpanStart,preSpanEnd-1,Spannable.SPAN_INCLUSIVE_INCLUSIVE)
              }
            }
            editable.insert(lineStart, Constants.ZERO_WIDTH_SPACE_STR)
            lineEnd = RichFunctionUtil.getThisLineEnd(editText, currentLine)
          }
          style.insertApplySpan(editable,lineStart,lineEnd,param)
        }else{
          //取消这段样式,已移除
        }
      }
    }else{ //选中了一段文字，修改这段文字的样式,只能修改非段落式的样式
      if (!style.canProcessSelectionText()) return //选中一段文字不处理样式
      val spans = editable.getSpans(selectionStart, selectionEnd, style.getSpanClass())
      if (apply){ //应用这段样式
        if (spans != null && spans.isNotEmpty()){//样式存在
          for (span in spans){
            val spanStart = editable.getSpanStart(span)
            val spanEnd = editable.getSpanEnd(span)
            if (spanStart >= selectionStart){ //样式大于选中开始位置
              if (spanEnd <= selectionEnd){ //样式在选中文字内部
                editable.removeSpan(span)
                val newSpan = style.newSpan(param)
                editable.setSpan(newSpan,selectionStart,selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
              }else{//样式在选中文件尾部超过
                editable.removeSpan(span)
                if (span is ICharacterSpan){
                  val spanCopy = span.newCopy()
                  editable.setSpan(spanCopy,selectionEnd,spanEnd,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
                  val newSpan = style.newSpan(param)
                  editable.setSpan(newSpan,selectionStart,selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
              }
            }else{ //样式开始在选中文字之前
              if (spanEnd <= selectionEnd){  //样式尾部不超过选中文字
                editable.removeSpan(span)
                if (span is ICharacterSpan){
                  val spanCopy = span.newCopy()
                  editable.setSpan(spanCopy,spanStart,selectionStart,Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                  val newSpan = style.newSpan(param)
                  editable.setSpan(newSpan,selectionStart,selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
              }else{//样式尾部超过选中文字
                editable.removeSpan(span)
                if (span is ICharacterSpan){
                  val spanCopy = span.newCopy()
                  editable.setSpan(spanCopy,spanStart,selectionStart,Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                  editable.setSpan(spanCopy,selectionEnd,spanEnd,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
                  val newSpan = style.newSpan(param)
                  editable.setSpan(newSpan,selectionStart,selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
              }
            }
          }
        }else{//处理添加一个样式
          val span = style.newSpan(param)
          editable.setSpan(span,selectionStart,selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
      }else{ //取消这段样式
        if (spans != null && spans.isNotEmpty()){
          for (span in spans){
            val spanStart = editable.getSpanStart(span)
            val spanEnd = editable.getSpanEnd(span)
            if (spanStart >= selectionStart){
              if (spanEnd <= selectionEnd){ //选中文字内部直接移除
                editable.removeSpan(span)
              }else { //样式尾部超过选中文字
                editable.removeSpan(span)
                if (span is ICharacterSpan){
                  val newSpan = span.newCopy()
                  editable.setSpan(newSpan,selectionEnd,spanEnd,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
                }
              }
            }else{
              if (spanEnd <= selectionEnd){ //选中文字头部超过
                editable.removeSpan(span)
                if (span is ICharacterSpan){
                  val newSpan = span.newCopy()
                  editable.setSpan(newSpan,spanStart,selectionStart,Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                }
              }else { //样式超过选中文字
                editable.removeSpan(span)
                if (span is ICharacterSpan){
                  val newSpan = span.newCopy()
                  editable.setSpan(newSpan,spanStart,selectionStart,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                  val newSpan1 = span.newCopy()
                  editable.setSpan(newSpan1,selectionEnd,spanEnd,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
                }
              }
            }
          }
        }else{
          //样式不存在不处理
        }
      }
    }
  }

  /**
   * 当输入改变的时候做出当前样式所有适配
   */
  fun <T : IStyle<*>> applyStyle(editText: EditText,styleArray:ArrayList<T>,editable:Editable,start: Int,end: Int) {
    val currentExistStyleList = mutableListOf<T>()
    for (style in styleArray){
      val currentListSpans = editable.getSpans(start, end, style.getSpanClass())
      if (null == currentListSpans || currentListSpans.isEmpty()) continue
      currentExistStyleList.add(style)
    }
    if (currentExistStyleList.isEmpty()) return
    if (end > start) { //输入文字
      val c = editable[end - 1] //最后输入的文字
      if (c == Constants.CHAR_NEW_LINE) { //输入了换行符
        val newSpanArray = mutableMapOf<IBaseStyle<*>,Any?>()
        for (style in currentExistStyleList){
          val currentListSpans = editable.getSpans(start, end, style.getSpanClass())
          val previousSpanIndex = currentListSpans.size - 1
          val previousSpan = currentListSpans[previousSpanIndex]
          val previousStart = editable.getSpanStart(previousSpan)
          if (end > previousStart) { //把样式从\n去掉
            editable.removeSpan(previousSpan)
            if (previousSpan is ICharacterSpan && style is IBaseStyle<*>){ //替换类的样式直接删除不添加了
              editable.setSpan(previousSpan, previousStart, end - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
              newSpanArray[style] = previousSpan.newCopy()
            }
          }
        }
        //从新行添加一个空字符并赋予上一个行的样式
        val currentLine = RichFunctionUtil.getCurrentCursorLine(editText)
        var startLine = RichFunctionUtil.getThisLineStart(editText, currentLine)
        editable.insert(startLine, Constants.ZERO_WIDTH_SPACE_STR)
        startLine = RichFunctionUtil.getThisLineStart(editText, currentLine)
        var endLine = RichFunctionUtil.getThisLineEnd(editText, currentLine)
        if (endLine > 0 && editable[endLine - 1] == Constants.CHAR_NEW_LINE) {
          endLine -= 1
        }
        for ((key,value) in newSpanArray){
          key.insertInputSpan(editable,startLine,endLine,value)
        }
      }else{//其他情况处理替换类样式，直接删除
        for (style in currentExistStyleList){
          val currentListSpans = editable.getSpans(start, end, style.getSpanClass())
          if (currentListSpans != null && currentListSpans.isNotEmpty()){
            for (span in currentListSpans){ //替换类直接删除
              if (span is IUnionTextSpan){
                editable.removeSpan(span)
              }
            }
          }
        }
      }
    }else{ //删除文字
      var currentEnd = end
      for (style in currentExistStyleList){
        val currentListSpans = editable.getSpans(start, end, style.getSpanClass())
        if (currentListSpans != null && currentListSpans.isNotEmpty()){
          for (span in currentListSpans){
            val spanStart = editable.getSpanStart(span)
            val spanEnd = editable.getSpanEnd(span)
            if (spanStart >= spanEnd) { // 用户上除了样式最后一个字符，直接移除样式
              editable.removeSpan(span)
            }
            if (span is IUnionTextSpan){ //整体类样式，直接移除样式
              editable.delete(spanStart,spanEnd)
              currentEnd = end - (spanEnd - spanStart)
            }
          }
        }
      }
      // 删除如果存在\n
      if (currentEnd > 0){
        val c = editable[currentEnd - 1]
        if (c == Constants.CHAR_NEW_LINE) {
          editable.delete(currentEnd - 1, currentEnd)
        }
      }
    }
  }

  /**
   * 插入一个IReplaceMentSpan
   */
  fun <T : IReplacementStyle<*>> insertReplacementSpan(editText: EditText,style:T,param: Any?){
    val editable = editText.editableText
    val selectionStart = editText.selectionStart
    val selectionEnd = editText.selectionEnd
    var insertPos = selectionEnd
    if (selectionEnd == selectionStart){ //选中文字时不做插入处理
      if (selectionEnd < 0){//第一行第一个直接插入时
        insertPos = 0
      }
      editable.insert(insertPos,param.toString())
      style.insertApplySpan(editable,insertPos,insertPos+param.toString().length,param)
    }
  }

  /**
   * 插入一个整体类型的span
   */
  fun <T : IUnionTextStyle<*>> insertUnionSpan(editText: EditText,style:T,param: Any?){
    val editable = editText.editableText
    val selectionStart = editText.selectionStart
    val selectionEnd = editText.selectionEnd
    var insertPos = selectionEnd
    if (selectionEnd == selectionStart){ //选中文字时不做插入处理
      if (selectionEnd < 0){//第一行第一个直接插入时
        insertPos = 0
      }
      if (param is Pair<*,*>){
        editable.insert(insertPos,param.first.toString())
        style.insertApplySpan(editable,insertPos,insertPos+param.first.toString().length,param)
      }
    }
  }

}