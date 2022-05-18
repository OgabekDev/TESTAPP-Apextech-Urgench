package dev.ogabek.testapp.model.login

import java.io.Serializable

data class LoginRespond(
    val login: String,
    val token: String,
    val fullName: String
): Serializable
