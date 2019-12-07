package com.renanferrari.iddog.common.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Context.inflate(res: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): View =
    LayoutInflater.from(this).inflate(res, parent, attachToRoot)