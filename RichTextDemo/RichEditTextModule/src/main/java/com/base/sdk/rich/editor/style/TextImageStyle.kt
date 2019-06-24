package com.base.sdk.rich.editor.style

import android.content.Context
import android.graphics.Bitmap
import com.base.sdk.rich.editor.span.TextImageSpan

/**
 * author:zack
 * Date:2019/5/31
 * Description:图片的style
 */
class TextImageStyle(private var context: Context) : BaseReplacementStyle<TextImageSpan>() {

  override fun getSpanClass(): Class<TextImageSpan> = TextImageSpan::class.java

  override fun newSpan(param: Any?): TextImageSpan = TextImageSpan(context, param as Bitmap)

}