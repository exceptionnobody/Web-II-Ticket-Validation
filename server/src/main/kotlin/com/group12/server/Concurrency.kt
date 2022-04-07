package com.group12.server

import com.codahale.usl4j.Measurement
import com.codahale.usl4j.Model

class ConcurrencyModel(lista: List<Pair<Int, Int>>) {
    private val model = Model.build(lista.asIterable().map {
        Measurement.ofConcurrency().andThroughput(it.first.toDouble(), it.second.toDouble())
    }
        .toMutableList())
    //val f = {i:Int -> println("At $i concurrent clients, expect ${model.throughputAtConcurrency(i.toDouble())} req/sec")}
    val g = {i:Int -> println("$i,${model.throughputAtConcurrency(i.toDouble())}")}

    fun constructAngPrintModel(numPoint: Int){
        for(i in 1..numPoint)
            g(i)
    }

}

fun main(){

    //val value8 = listOf((1 to 795), (2 to 1693), (4 to 1884),(8 to 1725), (16 to 1801), (32 to 1673), (64 to 1674))
    //val value8 = listOf((1 to 779), (2 to 1668), (4 to 1535),(8 to 1576), (16 to 1440), (32 to 1558), (64 to 1549))
    //val value8 = listOf((1 to 1883), (2 to 4704), (4 to 5070),(8 to 5076), (16 to 5113), (32 to 4823), (64 to 5312))
    //val value8 = listOf((1 to 318), (2 to 757), (4 to 1038),(8 to 1367), (16 to 1110), (32 to 1520), (64 to 1077))
    val value8 = listOf((1 to 832), (2 to 1165), (4 to 1704),(8 to 1811), (16 to 1530), (32 to 1538), (64 to 1253))
    println("Concurrency,Req/sec")
    ConcurrencyModel(value8).constructAngPrintModel(500)

}