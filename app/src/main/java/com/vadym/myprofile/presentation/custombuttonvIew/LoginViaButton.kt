package com.vadym.myprofile.presentation.custombuttonvIew

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.children
import com.vadym.myprofile.R
import com.vadym.myprofile.databinding.LayoutLoginViaButtonBinding

class LoginViaButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutLoginViaButtonBinding =
        LayoutLoginViaButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.LoginViaButton,
            defStyleAttr,
            0
        ).use {
            isEnabled = it.getBoolean(R.styleable.LoginViaButton_android_enabled, isEnabled)
            binding.apply {
                ivIconSocial.setImageResource(
                    it.getResourceId(
                        R.styleable.LoginViaButton_icon,
                        R.drawable.icon_google
                    )
                )
                tvNameSocial.text = it.getString(R.styleable.LoginViaButton_android_text)
            }
        }
        background = AppCompatResources.getDrawable(context, R.drawable.login_via_button_selector)
        isClickable = true
        isFocusable = true
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach { it.isEnabled = enabled }
    }
}