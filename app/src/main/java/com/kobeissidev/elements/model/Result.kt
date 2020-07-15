package com.kobeissidev.elements.model

data class Result<T>(val isSuccessful: Boolean, val body: T?, val code: Int?)