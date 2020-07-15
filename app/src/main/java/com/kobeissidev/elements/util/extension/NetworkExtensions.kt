package com.kobeissidev.elements.util.extension

import com.kobeissidev.elements.model.Result
import retrofit2.Response

val <T> Response<T>.result get() = Result(isSuccessful, body(), code())