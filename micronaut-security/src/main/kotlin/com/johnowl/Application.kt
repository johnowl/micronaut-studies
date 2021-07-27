package com.johnowl

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
	build()
		.banner(false)
	    .args(*args)
		.packages("com.johnowl")
		.start()
}

