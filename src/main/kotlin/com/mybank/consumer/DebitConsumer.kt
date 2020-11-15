package com.mybank.consumer

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.MongoDatabase
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import org.bson.Document
import org.reactivestreams.Publisher
import javax.inject.Inject


@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class DebitConsumer {

    @Inject
    //@Named("another")
    var mongoClient: MongoClient? = null


    @Topic("debit")
    fun receive(@KafkaKey key: String, name: String) {


        println("Account - $name by $key")


        var mongoDb : MongoDatabase? = mongoClient?.getDatabase("account")
        var mongoCollection: MongoCollection<Document>? = mongoDb?.getCollection("account_collection")
        var mongoDocument: Publisher<Document>? = mongoCollection?.find()?.first()
        print(mongoDocument.toString())

        //println(mongoClient?.getDatabase("account")?.getCollection("account_collection")?.find()?.first())
        //val mongoClientClient: MongoDatabase  = mongoClient.getDatabase("account")
        //println(mongoClient.getDatabase("account").getCollection("account_collection").find({ "size.h": { $lt: 15 } })
        //println(mongoClient.getDatabase("account").getCollection("account_collection").find("1").toString())


    }
}