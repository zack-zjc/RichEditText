package com.base.sdk.rich.editor.span

import android.content.Context
import android.graphics.Bitmap
import android.text.style.ImageSpan

/**
 * author:zack
 * Date:2019/5/31
 * Description:图片span,基本仿照imageSpan
 */
class TextImageSpan(private var context: Context,bitmap:Bitmap) : ImageSpan(context,bitmap) ,IReplacementSpan<TextImageSpan> {

  override fun newSpan(param: Any?): TextImageSpan = TextImageSpan(context, param as Bitmap)
}