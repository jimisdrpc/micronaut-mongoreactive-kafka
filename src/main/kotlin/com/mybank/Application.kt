package com.mybank

import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoClients
import io.micronaut.runtime.Micronaut.build
import org.bson.Document

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.mybank")
		.start()

}



