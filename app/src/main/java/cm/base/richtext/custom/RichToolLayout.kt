package cm.base.richtext.custom

import android.content.Context
import android.graphics.Color
import android.text.Layout.Alignment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.RelativeLayout
import cm.base.richtext.R
import com.base.sdk.rich.editor.Constants

/**
 * author:zack
 * Date:2019/5/31
 * Description:工具展示页面
 */
class RichToolLayout @JvmOverloads constructor(context: Context,attr: AttributeSet?=null,defaultAttr:Int = 0)
  : RelativeLayout(context,attr,defaultAttr){

  private val characterLayout : View
  private val sizeLayout : View
  private val alignLayout : View
  private val paragraphLayout : View
  private val colorLayout : View
  private val mBoldCheckBox : CheckBox
  private val mSkewCheckBox : CheckBox
  private val mUnderLineCheckBox : CheckBox
  private val mSmallSizeCheckBox : CheckBox
  private val mNormalSizeCheckBox : CheckBox
  private val mBigSizeCheckBox : CheckBox
  private val mAlignLeftCheckBox : CheckBox
  private val mAlignCenterCheckBox : CheckBox
  private val mAlignRightCheckBox : CheckBox
  private val mParagraphPointCheckBox : CheckBox
  private val mParagraphNumberCheckBox : CheckBox
  private val mColorBlackCheckBox : CheckBox
  private val mColorRedCheckBox : CheckBox
  private val mColorBlueCheckBox : CheckBox

  init {
    val view = LayoutInflater.from(context).inflate(R.layout.layout_base_pop_window_parent,this)
    characterLayout = view.findViewById(R.id.id_editor_tool_character_layout)
    sizeLayout = view.findViewById(R.id.id_editor_tool_size_layout)
    alignLayout = view.findViewById(R.id.id_editor_tool_align_layout)
    paragraphLayout = view.findViewById(R.id.id_editor_tool_paragraph_layout)
    colorLayout = view.findViewById(R.id.id_editor_tool_color_layout)
    mBoldCheckBox = view.findViewById(R.id.id_editor_tool_bold)
    mSkewCheckBox = view.findViewById(R.id.id_editor_tool_skew)
    mUnderLineCheckBox = view.findViewById(R.id.id_editor_tool_underline)
    mSmallSizeCheckBox = view.findViewById(R.id.id_editor_tool_size_small)
    mNormalSizeCheckBox = view.findViewById(R.id.id_editor_tool_size_normal)
    mBigSizeCheckBox = view.findViewById(R.id.id_editor_tool_size_big)
    mAlignLeftCheckBox = view.findViewById(R.id.id_editor_tool_align_left)
    mAlignCenterCheckBox = view.findViewById(R.id.id_editor_tool_align_center)
    mAlignRightCheckBox = view.findViewById(R.id.id_editor_tool_align_right)
    mParagraphPointCheckBox = view.findViewById(R.id.id_editor_tool_paragraph_point)
    mParagraphNumberCheckBox = view.findViewById(R.id.id_editor_tool_paragraph_number)
    mColorBlackCheckBox = view.findViewById(R.id.id_editor_tool_color_black)
    mColorRedCheckBox = view.findViewById(R.id.id_editor_tool_color_red)
    mColorBlueCheckBox = view.findViewById(R.id.id_editor_tool_color_blue)
  }

  /**
   * 展示文字样式的弹框
   */
  fun showTextCharacterPopWindow(clickListener: OnClickListener,boldEnable:Boolean,skewEnable:Boolean,underLineEnable:Boolean){
    showLayout(characterEnable = true)
    mBoldCheckBox.isChecked = boldEnable
    mSkewCheckBox.isChecked = skewEnable
    mUnderLineCheckBox.isChecked = underLineEnable
    mBoldCheckBox.setOnClickListener(clickListener)
    mSkewCheckBox.setOnClickListener(clickListener)
    mUnderLineCheckBox.setOnClickListener(clickListener)
  }

  /**
   * 展示文字居中样式的弹框
   */
  fun showTextAlignPopWindow(clickListener: OnClickListener,textAlign: Alignment){
    showLayout(textAlignEnable = true)
    mAlignLeftCheckBox.isChecked = textAlign == Alignment.ALIGN_LEFT
    mAlignCenterCheckBox.isChecked = textAlign == Alignment.ALIGN_CENTER
    mAlignRightCheckBox.isChecked = textAlign == Alignment.ALIGN_RIGHT
    mAlignLeftCheckBox.setOnClickListener{
      mAlignCenterCheckBox.isChecked = false
      mAlignRightCheckBox.isChecked = false
      clickListener.onClick(it)
    }
    mAlignCenterCheckBox.setOnClickListener{
      mAlignLeftCheckBox.isChecked = false
      mAlignRightCheckBox.isChecked = false
      clickListener.onClick(it)
    }
    mAlignRightCheckBox.setOnClickListener{
      mAlignLeftCheckBox.isChecked = false
      mAlignCenterCheckBox.isChecked = false
      clickListener.onClick(it)
    }
  }

  /**
   * 展示文字大小
   */
  fun showTextSizePopWindow(clickListener: OnClickListener,textSize:Float){
    showLayout(textSizeEnable = true)
    mSmallSizeCheckBox.isChecked = textSize == Constants.DEFAULT_FONT_SMALL_SIZE
    mNormalSizeCheckBox.isChecked = textSize == Constants.DEFAULT_FONT_SIZE
    mBigSizeCheckBox.isChecked = textSize == Constants.DEFAULT_FONT_BIG_SIZE
    mSmallSizeCheckBox.setOnClickListener{
      mNormalSizeCheckBox.isChecked = false
      mBigSizeCheckBox.isChecked = false
      clickListener.onClick(it)
    }
    mNormalSizeCheckBox.setOnClickListener{
      mSmallSizeCheckBox.isChecked = false
      mBigSizeCheckBox.isChecked = false
      clickListener.onClick(it)
    }
    mBigSizeCheckBox.setOnClickListener{
      mSmallSizeCheckBox.isChecked = false
      mNormalSizeCheckBox.isChecked = false
      clickListener.onClick(it)
    }
  }

  /**
   * 展示段落状态的弹框
   */
  fun showTextParagraphPopWindow(clickListener: OnClickListener,numberEnable:Boolean,pointEnable:Boolean){
    showLayout(paragraphEnable = true)
    mParagraphNumberCheckBox.isChecked = numberEnable
    mParagraphPointCheckBox.isChecked = pointEnable
    mParagraphPointCheckBox.setOnClickListener{
      mParagraphNumberCheckBox.isChecked = false
      clickListener.onClick(it)
    }
    mParagraphNumberCheckBox.setOnClickListener{
      mParagraphPointCheckBox.isChecked = false
      clickListener.onClick(it)
    }
  }

  /**
   * 展示文字颜色的弹框
   */
  fun showTextColorPopWindow(clickListener: OnClickListener,textColor:Int){
    showLayout(textColorEnable = true)
    mColorBlackCheckBox.isChecked = textColor == Color.BLACK
    mColorRedCheckBox.isChecked = textColor == Color.RED
    mColorBlueCheckBox.isChecked = textColor == Color.BLUE
    mColorBlackCheckBox.setOnClickListener{
      mColorRedCheckBox.isChecked = false
      mColorBlueCheckBox.isChecked = false
      clickListener.onClick(it)
    }
    mColorRedCheckBox.setOnClickListener{
      mColorBlackCheckBox.isChecked = false
      mColorBlueCheckBox.isChecked = false
      clickListener.onClick(it)
    }
    mColorBlueCheckBox.setOnClickListener{
      mColorBlackCheckBox.isChecked = false
      mColorRedCheckBox.isChecked = false
      clickListener.onClick(it)
    }
  }

  /**
   * 隐藏所有样式弹框
   */
  fun hideLayout(){
    showLayout()
  }

  /**
   * 展示布局
   */
  private fun showLayout(characterEnable:Boolean = false,textSizeEnable:Boolean = false,textColorEnable:Boolean = false,
    paragraphEnable:Boolean = false,textAlignEnable:Boolean = false){
    characterLayout.visibility = if (characterEnable) View.VISIBLE else View.GONE
    sizeLayout.visibility = if (textSizeEnable) View.VISIBLE else View.GONE
    colorLayout.visibility = if (textColorEnable) View.VISIBLE else View.GONE
    paragraphLayout.visibility = if (paragraphEnable) View.VISIBLE else View.GONE
    alignLayout.visibility = if (textAlignEnable) View.VISIBLE else View.GONE
  }
}