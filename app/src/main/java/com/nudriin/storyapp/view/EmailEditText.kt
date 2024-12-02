package com.nudriin.storyapp.view

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validateEmail()
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }

        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Email"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun validateEmail(): Boolean {
        return when {
            this.text.toString().isEmpty() -> {
                this.error = "Email is required"
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(this.text.toString()).matches() -> {
                this.error = "Email invalid"
                false
            }

            else -> {
                this.error = null
                true
            }
        }
    }
}