package com.renanferrari.iddog.common.extensions

import androidx.core.util.PatternsCompat

fun String.isValidEmail(): Boolean = PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()