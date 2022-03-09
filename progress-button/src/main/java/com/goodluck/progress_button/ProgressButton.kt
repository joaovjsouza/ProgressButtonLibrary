package com.goodluck.progress_button

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.goodluck.progress_button.databinding.ResProgressButtonBinding

sealed class ProgressButtonState(val isEnabled: Boolean) {
    object Idle : ProgressButtonState(true)
    object Loading : ProgressButtonState(false)
}

class ProgressButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {
    private val binding = ResProgressButtonBinding.inflate(LayoutInflater.from(context), this, true)
    val errorCode = -1000

    var buttonText: String? = null
        set(value) {
            field = value
            binding.buttonText.text = if (field.isNullOrEmpty()) "Button Text Here"
            else field
        }

    var buttonBackground: Int? = null
        @SuppressLint("UseCompatLoadingForDrawables")
        set(value) {
            field = value
            if (field != errorCode) binding.buttonLayout.setBackgroundResource(field!!)
            else context.getDrawable(R.drawable.default_progress_button_bg)
        }

    var colorText: Int? = null
        @SuppressLint("NewApi")
        set(value) {
            field = value
            if (field != errorCode) binding.buttonText.setTextColor(field!!)
            else context.getColor(R.color.white)
        }

    var colorProgress: Int? = null
        @SuppressLint("NewApi")
        set(value) {
            field = value
            if (field != errorCode) binding.progress.indeterminateTintList =
                ColorStateList.valueOf(field!!) else context.getColorStateList(R.color.orange)
        }

    var state: ProgressButtonState = ProgressButtonState.Idle
        set(value) {
            field = value
            refreshState()
        }

    init {
        setLayout(attrs)
        refreshState()
    }

    private fun refreshState() {
        binding.buttonLayout.run {
            isClickable = state.isEnabled
            isEnabled = state.isEnabled
        }

        binding.buttonText.visibility = if (state.isEnabled) VISIBLE else GONE
        binding.progress.visibility = if (state.isEnabled) GONE else VISIBLE
    }

    private fun setLayout(attributes: AttributeSet?) {
        attributes?.let {
            val attrs = context.obtainStyledAttributes(it, R.styleable.ProgressButton)

            buttonText = attrs.getString(R.styleable.ProgressButton_button_text)
            buttonBackground =
                attrs.getResourceId(R.styleable.ProgressButton_button_background, errorCode)
            colorText = attrs.getColor(R.styleable.ProgressButton_color_text, errorCode)
            colorProgress = attrs.getColor(R.styleable.ProgressButton_color_progress, errorCode)

            attrs.recycle()
        }
    }

    fun setOnClickListener(click: (View) -> Unit) {
        binding.buttonLayout.setOnClickListener {
            click(it)
        }
    }

    fun setIdle() {
        state = ProgressButtonState.Idle
    }

    fun setLoading() {
        state = ProgressButtonState.Loading
    }
}