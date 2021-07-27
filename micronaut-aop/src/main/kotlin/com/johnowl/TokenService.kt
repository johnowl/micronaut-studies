package com.johnowl

import javax.inject.Singleton

@Singleton
class TokenService {

    fun validate(token: String) {
        throw Exception("Invalid scope")
    }

}