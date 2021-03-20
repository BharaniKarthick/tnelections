package com.example.tnelection2021.views
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.tnelection2021.utils.ViewUtils


class RubikLightItalicTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs)
{
    override fun setTypeface(tf: Typeface?)
    {
        super.setTypeface(ViewUtils.getRubikLightItalicTypeface(context))
    }
}