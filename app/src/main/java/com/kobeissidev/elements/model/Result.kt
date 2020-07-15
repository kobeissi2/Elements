package com.kobeissidev.elements.model

/**
 * Result object using generics.
 * @param isSuccessful will let us know if the result was a success.
 * @param body contains the data if the data was successful.
 * @param code contains the response code.
 */
data class Result<T>(val isSuccessful: Boolean, val body: T?, val code: Int?)