package com.goodluck.progress_button

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.goodluck.progress_button.databinding.ResProgressButtonBinding

class ProgressButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {
    private val binding = ResProgressButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private val button = binding.button
    private val progressBar = binding.progressBar
    private var idleColor: Int? = null
    private var errorColor: Int? = null
    private val errorCode = -1000
    private var idleButtonText: String? = null
    private var errorButtonText: String? = null

    private var state: ProgressButtonState = ProgressButtonState.Idle
        set(value) {
            field = value
            refreshState()
        }

    private fun refreshState() {
        when (state) {
            ProgressButtonState.Loading -> {
                button.visibility = GONE
                progressBar.visibility = VISIBLE
            }
            ProgressButtonState.Error -> {
                button.text = errorButtonText
                button.visibility = VISIBLE
                progressBar.visibility = GONE
                button.setBackgroundColor(errorColor!!)
            }
            else -> {
                button.text = idleButtonText
                button.visibility = VISIBLE
                progressBar.visibility = GONE
                button.setBackgroundColor(idleColor!!)
            }
        }
    }

    init {
        setLayout(attrs)
        refreshState()
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.ProgressButton)

            val idleColorAttr =
                attributes.getColor(R.styleable.ProgressButton_idle_color, errorCode)
            val errorColorAttr =
                attributes.getColor(R.styleable.ProgressButton_error_color, errorCode)
            val progressBarSizeAttr =
                attributes.getDimensionPixelSize(R.styleable.ProgressButton_progress_bar_size,
                    errorCode)
            val progressBarColorAttr =
                attributes.getColor(R.styleable.ProgressButton_progress_bar_color, errorCode)
            val idleButtonTextAttr = attributes.getString(R.styleable.ProgressButton_idle_text)
            val errorButtonTextAttr = attributes.getString(R.styleable.ProgressButton_error_text)

            if (!idleButtonTextAttr.isNullOrEmpty()) {
                idleButtonText = idleButtonTextAttr
            }

            if (!errorButtonTextAttr.isNullOrEmpty()) {
                errorButtonText = errorButtonTextAttr
            }


            if (progressBarSizeAttr != errorCode && progressBarColorAttr != errorCode) {
                progressBar.run {
                    maxHeight = progressBarSizeAttr
                    minHeight = progressBarSizeAttr
                    indeterminateTintList = ColorStateList.valueOf(progressBarColorAttr
                    )
                }
            }

            if (idleColorAttr != errorCode) idleColor = idleColorAttr
            if (errorColorAttr != errorCode) errorColor = errorColorAttr



            attributes.recycle()
        }
    }

    fun setLoading() {
        state = ProgressButtonState.Loading
        refreshState()
    }

    fun setError() {
        state = ProgressButtonState.Error
    }

    fun setOnClickListener(onClick: () -> Unit) {
        button.setOnClickListener {
            onClick()
        }
    }

    fun setIdle() {
        state = ProgressButtonState.Idle
        refreshState()
    }

    sealed class ProgressButtonState(val isLoading: Boolean, val color: Int?) {
        object Idle : ProgressButtonState(isLoading = false, color = 0xff9800)
        object Loading : ProgressButtonState(isLoading = true, color = null)
        object Error : ProgressButtonState(isLoading = false, color = 0xF44336)
    }
}