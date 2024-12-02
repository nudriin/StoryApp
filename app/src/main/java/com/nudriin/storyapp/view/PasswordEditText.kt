package com.nudriin.storyapp.view

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validatePassword()
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }

        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Password"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun validatePassword(): Boolean {
        return when {
            this.text.toString().isEmpty() -> {
                this.error = "Password is required"
                false
            }

            this.text.toString().length < 8 -> {
                this.error = "Password must be greater than equals 8"
                false
            }

            else -> {
                this.error = null
                true
            }
        }
    }
}