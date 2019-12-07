package com.renanferrari.iddog.common.utils

import androidx.core.util.PatternsCompat

fun String.isValidEmail(): Boolean = PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()