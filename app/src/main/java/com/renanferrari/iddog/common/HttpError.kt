package com.renanferrari.iddog.common

class HttpError(code: Int) : Throwable("HTTP error: $code")