package com.base.sdk.rich.editor.span

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.Browser
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View

/**
 * author:zack
 * Date:2019/5/31
 * Description:文字链接span
 */
class TextLinkSpan(param: Any?) : ClickableSpan() ,IUnionTextSpan<TextLinkSpan> {

  //跳转网址
  private var linkUrl:Any? = null

  init {
    if (param is Pair<*, *>){
      linkUrl = param.second
    }
  }

  override fun onClick(widget: View) {
    if (linkUrl != null) return
    val uri = Uri.parse(linkUrl.toString())
    val context = widget.context
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.packageName)
    try {
      context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
      Log.w("URLSpan", "Actvity was not found for intent, " + intent.toString())
    }
  }

  override fun updateDrawState(textPaint: TextPaint) {
    textPaint.color = textPaint.linkColor
    textPaint.isUnderlineText = false
  }

  override fun newSpan(param: Any?): TextLinkSpan = TextLinkSpan(param)

}