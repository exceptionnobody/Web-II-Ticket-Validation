package com.group12.server

import com.codahale.usl4j.Measurement
import com.codahale.usl4j.Model


fun main(args: Array<String>){
    val value = listOf((1 to 464), (2 to 866), (4 to 1281), (8 to 1310), (16 to 1259), (32 to 1475))
    val model = Model.build(value.asIterable().map {
        Measurement.ofConcurrency().andThroughput(it.first.toDouble(), it.second.toDouble())
    }
        .toMutableList())
    val f = {i:Int -> println("At $i concurrent clients, expect ${model.throughputAtConcurrency(i.toDouble())} req/sec")}
    val g = {i:Int -> println("$i ${model.throughputAtConcurrency(i.toDouble())}")}
/*
    f(10)
    f(50)
    f(100)
    f(500)
    f(900)
    f(1000)
*/
    for(i in 1..1000)
        g(i)
}