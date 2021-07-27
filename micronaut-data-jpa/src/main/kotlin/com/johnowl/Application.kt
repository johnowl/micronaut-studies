package com.johnowl

import io.micronaut.runtime.Micronaut.build


fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.johnowl")
		.start()
}