package com.base.sdk.rich.editor.widget

import android.content.Context
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.base.sdk.rich.editor.Constants
import com.base.sdk.rich.editor.style.IReplacementStyle
import com.base.sdk.rich.editor.style.IStyle
import com.base.sdk.rich.editor.style.IUnionTextStyle
import com.base.sdk.rich.editor.util.RichStyleUtil
import com.base.sdk.rich.editor.widget.callback.SelectionChangeListener
import java.util.Stack

/**
 * author:zack
 * Date:2019/5/28
 * Description:富文本编辑器
 */
class RichEditText @JvmOverloads constructor(context: Context,attr: AttributeSet?=null,defaultAttr:Int = 0) : EditText(context,attr,defaultAttr) {

  private var mStylesList : ArrayList<IStyle<*>> = ArrayList()

  private var mSelectionChangeListener : SelectionChangeListener? = null

  private var editableBackStack = Stack<SpannableString>()

  private var editableForwardStack = Stack<SpannableString>()

  private val textWatcher:TextWatcher = object :TextWatcher{

    var startPos = 0

    var endPos = 0

    override fun afterTextChanged(editable: Editable?) {
      editable?.let {
        removeTextChangedListener(this)
        RichStyleUtil.applyStyle(this@RichEditText,mStylesList,it,startPos,endPos)
        editableBackStack.push(SpannableString(editable))
        addTextChangedListener(this)
      }
    }

    override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {

    }

    override fun onTextChanged(s: CharSequence?,start: Int,before: Int,count: Int) {
      this.startPos = start
      this.endPos = start + count
    }

  }

  init {
    this.isFocusableInTouchMode = true
    addTextChangedListener(textWatcher)
  }

  /**
   * 添加所有适配的属性
   */
  fun setEnableStyle(styleList:ArrayList<out IStyle<*>>){
    mStylesList.addAll(styleList)
  }

  /**
   * 光标位置触发器
   */
  fun setSelectionListener(listener:SelectionChangeListener){
    this.mSelectionChangeListener = listener
  }

  /**
   * 适配某个样式
   */
  fun setStyleParam(styleClass:Class<out IStyle<*>>,apply:Boolean,param:Any?=null){
    for (style in mStylesList){
      if (style.javaClass == styleClass){
        RichStyleUtil.startApplyStyle(this,style,apply,param)
      }
    }
  }

  /**
   * 插入某个样式
   */
  fun insertReplacementSpan(styleClass:Class<out IReplacementStyle<*>>,param:Any?=null){
    for (style in mStylesList){
      if (style.javaClass == styleClass && style is IReplacementStyle<*>){
        removeTextChangedListener(textWatcher)
        RichStyleUtil.insertReplacementSpan(this,style,param)
        editableBackStack.push(SpannableString(editableText))
        addTextChangedListener(textWatcher)
      }
    }
  }

  /**
   * 插入一段文字包含某个样式
   */
  fun insertUnionSpan(styleClass:Class<out IUnionTextStyle<*>>,param:Any?=null){
    for (style in mStylesList){
      if (style.javaClass == styleClass && style is IUnionTextStyle<*>){
        removeTextChangedListener(textWatcher)
        RichStyleUtil.insertUnionSpan(this,style,param)
        editableBackStack.push(SpannableString(editableText))
        addTextChangedListener(textWatcher)
      }
    }
  }

  /**
   * 后退之前的编辑
   * 未处理好
   */
  fun backEdit(){
    if(editableBackStack.isNotEmpty()){
      val editHistory = editableBackStack.pop()
      editableForwardStack.push(editHistory)
      removeTextChangedListener(textWatcher)
      if (editableBackStack.isNotEmpty()){
        val lastHistory = editableBackStack.pop()
        setText(lastHistory)
      }else {
        text = null
      }
      addTextChangedListener(textWatcher)
    }
  }

  /**
   * 前进之前的编辑
   * 未处理好
   */
  fun forwardEdit(){
    if(editableForwardStack.isNotEmpty()){
      val editForward = editableForwardStack.pop()
      editableBackStack.push(editForward)
      removeTextChangedListener(textWatcher)
      setText(editForward)
      addTextChangedListener(textWatcher)
    }
  }

  /**
   * 光标位置修改触发
   */
  override fun onSelectionChanged(selectStart: Int,selectEnd: Int) {
    //当前确认光标位置,获取当前段落的样式
    mSelectionChangeListener?.onSelectionChanged(selectStart,selectEnd)
  }

}