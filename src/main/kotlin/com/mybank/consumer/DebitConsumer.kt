package com.mybank.consumer

import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.messaging.annotation.Body
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import io.reactivex.functions.Function

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class DebitConsumer {

    @Inject
    lateinit var mongoClient: MongoClient

    @Topic("debit")
    fun receiveBody(@KafkaKey key: String, @Body debitMessage: DebitMessage) {

        Observable.just(key)
            .subscribe { n -> println(n) }

        Single
                .fromPublisher(getCollection().insertOne(debitMessage))
                .map<DebitMessage>(Function<InsertOneResult, DebitMessage> { debitMessage })
                .subscribe()
    }

    private fun getCollection(): MongoCollection<DebitMessage?> {
        return mongoClient
                .getDatabase("mydb")
                .getCollection("mycollection", DebitMessage::class.java)
    }
}