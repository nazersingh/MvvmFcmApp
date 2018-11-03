package com.nazer.saini.fcmapp.util

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.support.design.widget.TextInputLayout
import android.databinding.BindingAdapter


object TextInputValidation {

    @BindingAdapter("app:validation", "app:errorMsg")
    fun setErrorEnable(textInputLayout: TextInputLayout, stringRule: StringRule,errorMsg: String) {
        textInputLayout.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                textInputLayout.isErrorEnabled = stringRule.validate(textInputLayout.editText!!.text)

                if (stringRule.validate(textInputLayout.editText!!.text)) {
                    textInputLayout.error = errorMsg
                } else {
                    textInputLayout.error = null
                }
            }
        })

        textInputLayout.isErrorEnabled = stringRule.validate(textInputLayout.editText!!.text)
        if (stringRule.validate(textInputLayout.editText!!.text)) {
            textInputLayout.error = errorMsg
        } else {
            textInputLayout.error = null
        }
    }

    object Rule {

        var NOT_EMPTY_RULE = { TextUtils.isEmpty("error") }
//        var EMAIL_RULE = { s.toString().length > 18 }
    }

    interface StringRule {

        fun validate(s: Editable): Boolean
    }
}