package com.ssepulveda.presentation_common.ui

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringResolve @Inject constructor(@ApplicationContext private val context: Context) {

    fun getString(@StringRes idRes: Int, vararg args: Any?) =
        context.resources.getString(idRes, args)
}