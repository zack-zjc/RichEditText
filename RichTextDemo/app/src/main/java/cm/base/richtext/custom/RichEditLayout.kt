package cm.base.richtext.custom

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.text.Layout.Alignment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.CheckBox
import android.widget.RelativeLayout
import cm.base.richtext.R
import com.base.sdk.rich.editor.Constants
import com.base.sdk.rich.editor.RichTextLib
import com.base.sdk.rich.editor.span.TextAlignSpan
import com.base.sdk.rich.editor.span.TextBoldSpan
import com.base.sdk.rich.editor.span.TextColorSpan
import com.base.sdk.rich.editor.span.TextParagraphNumberSpan
import com.base.sdk.rich.editor.span.TextParagraphPointSpan
import com.base.sdk.rich.editor.span.TextSizeSpan
import com.base.sdk.rich.editor.span.TextSkewSpan
import com.base.sdk.rich.editor.span.TextUnderLineSpan
import com.base.sdk.rich.editor.style.TextAlignStyle
import com.base.sdk.rich.editor.style.TextBoldStyle
import com.base.sdk.rich.editor.style.TextColorStyle
import com.base.sdk.rich.editor.style.TextImageStyle
import com.base.sdk.rich.editor.style.TextLinkStyle
import com.base.sdk.rich.editor.style.TextParagraphNumberStyle
import com.base.sdk.rich.editor.style.TextParagraphPointStyle
import com.base.sdk.rich.editor.style.TextSizeStyle
import com.base.sdk.rich.editor.style.TextSkewStyle
import com.base.sdk.rich.editor.style.TextUnderLineStyle
import com.base.sdk.rich.editor.util.RichFunctionUtil
import com.base.sdk.rich.editor.widget.RichEditText
import com.base.sdk.rich.editor.widget.callback.SelectionChangeListener

/**
 * author:zack
 * Date:2019/5/29
 * Description:自定义包含样式选择的view
 */
class RichEditLayout @JvmOverloads constructor(context: Context,attr: AttributeSet?=null,defaultAttr:Int = 0)
  : RelativeLayout(context,attr,defaultAttr),OnClickListener, SelectionChangeListener {

  private var mRichEditText : RichEditText
  private var mRichToolLayout : RichToolLayout

  private var mBoldEnable :Boolean = false
  private var mSkewEnable :Boolean = false
  private var mUnderLineEnable :Boolean = false
  private var mTextAlign :Alignment = Alignment.ALIGN_LEFT
  private var mTextSize :Float = Constants.DEFAULT_FONT_SIZE
  private var mTextColor :Int = Color.BLACK
  private var mParagraphNumberEnable :Boolean = false
  private var mParagraphPointEnable :Boolean = false

  init {
    RichTextLib.setEnableStyle(arrayListOf(TextBoldStyle(),TextSkewStyle(),TextUnderLineStyle(), TextSizeStyle(),TextColorStyle(),
        TextAlignStyle(),TextParagraphNumberStyle(),TextParagraphPointStyle(),TextLinkStyle(),TextImageStyle(context)))
    val view = LayoutInflater.from(context).inflate(R.layout.layout_base_rich_edit_view,this)
    mRichToolLayout = view.findViewById(R.id.id_editor_tool_layout)
    mRichEditText = view.findViewById(R.id.id_editor_view)
    mRichEditText.setOnClickListener{mRichToolLayout.hideLayout()}
    mRichEditText.setSelectionListener(this)
    view.findViewById<View>(R.id.id_editor_tool_character_image).setOnClickListener(this)
    view.findViewById<View>(R.id.id_editor_tool_align_image).setOnClickListener(this)
    view.findViewById<View>(R.id.id_editor_tool_paragraph_image).setOnClickListener(this)
    view.findViewById<View>(R.id.id_editor_tool_size_image).setOnClickListener(this)
    view.findViewById<View>(R.id.id_editor_tool_color_image).setOnClickListener(this)
    view.findViewById<View>(R.id.id_editor_tool_add_link_image).setOnClickListener(this)
    view.findViewById<View>(R.id.id_editor_tool_add_image_image).setOnClickListener(this)
  }

  /**
   * 编辑器选中状态修改
   */
  override fun onSelectionChanged(selectStart: Int,selectEnd: Int) {
    if (selectStart == selectEnd){ //只修改定位光标是当前段落的状态
      val lineNumber = RichFunctionUtil.getCurrentCursorLine(mRichEditText)
      val lineStart = RichFunctionUtil.getThisLineStart(mRichEditText,lineNumber)
      val lineEnd = RichFunctionUtil.getThisLineEnd(mRichEditText,lineNumber)
      var textAlign = Alignment.ALIGN_LEFT
      val textAlignSpanList = mRichEditText.editableText.getSpans(lineStart,lineEnd,TextAlignSpan::class.java)
      if (textAlignSpanList != null && textAlignSpanList.isNotEmpty()){
        textAlign = textAlignSpanList[textAlignSpanList.size-1].alignment
      }
      val textBoldSpanList = mRichEditText.editableText.getSpans(lineStart,lineEnd,TextBoldSpan::class.java)
      val textBold = textBoldSpanList != null && textBoldSpanList.isNotEmpty()
      var textColor = Color.BLACK
      val textColorSpanList = mRichEditText.editableText.getSpans(lineStart,lineEnd,TextColorSpan::class.java)
      if (textColorSpanList != null && textColorSpanList.isNotEmpty()){
        textColor = textColorSpanList[textColorSpanList.size-1].color
      }
      val textParagraphNumberSpanList = mRichEditText.editableText.getSpans(lineStart,lineEnd,TextParagraphNumberSpan::class.java)
      val textParagraphNumberEnable = textParagraphNumberSpanList != null && textParagraphNumberSpanList.isNotEmpty()
      val textParagraphPointSpanList = mRichEditText.editableText.getSpans(lineStart,lineEnd,TextParagraphPointSpan::class.java)
      val textParagraphPointEnable = textParagraphPointSpanList != null && textParagraphPointSpanList.isNotEmpty()
      var textSize = Constants.DEFAULT_FONT_SIZE
      val textSizeSpanList = mRichEditText.editableText.getSpans(lineStart,lineEnd,TextSizeSpan::class.java)
      if (textSizeSpanList != null && textSizeSpanList.isNotEmpty()){
        textSize = textSizeSpanList[textSizeSpanList.size-1].textSize
      }
      val textSkewSpanList = mRichEditText.editableText.getSpans(lineStart,lineEnd,TextSkewSpan::class.java)
      val textSkew = textSkewSpanList != null && textSkewSpanList.isNotEmpty()
      val textUnderlineSpanList = mRichEditText.editableText.getSpans(lineStart,lineEnd,TextUnderLineSpan::class.java)
      val textUnderline = textUnderlineSpanList != null && textUnderlineSpanList.isNotEmpty()
      updateParagraphState(textBold,textSkew,textUnderline,textColor,textSize,textAlign,textParagraphNumberEnable,textParagraphPointEnable)
    }else{
      updateParagraphState(false,false,false,Color.BLACK,Constants.DEFAULT_FONT_SIZE,Alignment.ALIGN_LEFT,false,false)
    }
  }

  /**
   * 点击状态触发
   */
  override fun onClick(view: View?) {
    if (view != null){
      if (view is CheckBox){
        when(view.id){
          R.id.id_editor_tool_bold -> setParagraphBold(view.isChecked)
          R.id.id_editor_tool_skew -> setParagraphSkew(view.isChecked)
          R.id.id_editor_tool_underline -> setParagraphUnderLine(view.isChecked)
          R.id.id_editor_tool_align_left -> setParagraphAlign(Alignment.ALIGN_LEFT)
          R.id.id_editor_tool_align_center -> setParagraphAlign(Alignment.ALIGN_CENTER)
          R.id.id_editor_tool_align_right -> setParagraphAlign(Alignment.ALIGN_RIGHT)
          R.id.id_editor_tool_paragraph_point -> setParagraphLinePoint(view.isChecked)
          R.id.id_editor_tool_paragraph_number -> setParagraphLineNumber(view.isChecked)
          R.id.id_editor_tool_color_black -> setParagraphColor(Color.BLACK)
          R.id.id_editor_tool_color_red -> setParagraphColor(Color.RED)
          R.id.id_editor_tool_color_blue -> setParagraphColor(Color.BLUE)
          R.id.id_editor_tool_size_small -> setParagraphSize(Constants.DEFAULT_FONT_SMALL_SIZE)
          R.id.id_editor_tool_size_normal -> setParagraphSize(Constants.DEFAULT_FONT_SIZE)
          R.id.id_editor_tool_size_big -> setParagraphSize(Constants.DEFAULT_FONT_BIG_SIZE)
        }
      }else {
        when(view.id){
          R.id.id_editor_tool_character_image -> {
            mRichToolLayout.showTextCharacterPopWindow(this,mBoldEnable,mSkewEnable,mUnderLineEnable)
          }
          R.id.id_editor_tool_align_image -> {
            mRichToolLayout.showTextAlignPopWindow(this,mTextAlign)
          }
          R.id.id_editor_tool_paragraph_image -> {
            mRichToolLayout.showTextParagraphPopWindow(this,mParagraphNumberEnable,mParagraphPointEnable)
          }
          R.id.id_editor_tool_size_image -> {
            mRichToolLayout.showTextSizePopWindow(this,mTextSize)
          }
          R.id.id_editor_tool_color_image -> {
            mRichToolLayout.showTextColorPopWindow(this,mTextColor)
          }
          R.id.id_editor_tool_add_image_image->{
            mRichEditText.insertReplacementSpan(TextImageStyle::class.java,BitmapFactory.decodeResource(resources,R.drawable.edit_tool_skew))
          }
          R.id.id_editor_tool_add_link_image->{
            mRichEditText.insertUnionSpan(TextLinkStyle::class.java,Pair("百度","http://www.baidu.com"))
          }
        }
      }
    }
  }

  /**
   * 更新段落文字状态
   */
  private fun updateParagraphState(textBold:Boolean,textSkew:Boolean,textUnderline:Boolean,textColor:Int,textSize:Float,textAlign:Alignment,
    textParagraphNumberEnable:Boolean,textParagraphPointEnable:Boolean){
    this.mBoldEnable = textBold
    this.mSkewEnable = textSkew
    this.mUnderLineEnable = textUnderline
    this.mTextAlign = textAlign
    this.mParagraphNumberEnable = textParagraphNumberEnable
    this.mParagraphPointEnable = textParagraphPointEnable
    this.mTextSize = textSize
    this.mTextColor = textColor
  }

  /**
   * 设置当前段落加粗与否
   */
  private fun setParagraphBold(boldEnable:Boolean){
    mRichEditText.setStyleParam(TextBoldStyle::class.java,boldEnable)
  }

  /**
   * 设置当前段落斜体与否
   */
  private fun setParagraphSkew(skewEnable:Boolean){
    mRichEditText.setStyleParam(TextSkewStyle::class.java,skewEnable)
  }

  /**
   * 设置当前段落下划线与否
   */
  private fun setParagraphUnderLine(underLineEnable:Boolean){
    mRichEditText.setStyleParam(TextUnderLineStyle::class.java,underLineEnable)
  }

  /**
   * 设置当前段落文字颜色
   */
  private fun setParagraphColor(color:Int){
    mRichEditText.setStyleParam(TextColorStyle::class.java,color != Color.BLACK ,color)
  }

  /**
   * 设置当前段落文字大小
   */
  private fun setParagraphSize(textSize:Float){
    mRichEditText.setStyleParam(TextSizeStyle::class.java,textSize != Constants.DEFAULT_FONT_SIZE ,textSize)
  }

  /**
   * 设置当前段落文字依靠
   */
  private fun setParagraphAlign(align:Alignment){
    mRichEditText.setStyleParam(TextAlignStyle::class.java,align != Alignment.ALIGN_LEFT ,align)
  }

  /**
   * 设置当前段落文字数字行标
   */
  private fun setParagraphLineNumber(lineNumberEnable:Boolean){
    mRichEditText.setStyleParam(TextParagraphNumberStyle::class.java,lineNumberEnable)
  }

  /**
   * 设置当前段落文字加点行标
   */
  private fun setParagraphLinePoint(linePointEnable:Boolean){
    mRichEditText.setStyleParam(TextParagraphPointStyle::class.java,linePointEnable)
  }

}