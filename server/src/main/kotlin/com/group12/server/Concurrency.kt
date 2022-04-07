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
    // val value1 = listOf((1 to 451), (2 to 1039), (4 to 1223), (8 to 1256), (16 to 1338), (32 to 1332), (64 to 1320))
    // ConcurrencyModel(value1).constructAngPrintModel(500)

    //val value2 = listOf((1 to 478), (2 to 1052), (4 to 1271), (8 to 1320), (16 to 1354), (32 to 1465), (64 to 1488))
    //ConcurrencyModel(value2).constructAngPrintModel(500)

    //val value3 = listOf((1 to 478), (2 to 1032), (4 to 1362),(8 to 1348), (16 to 1377), (32 to 1470), (64 to 1446))
    //ConcurrencyModel(value3).constructAngPrintModel(500)

    //val value4 = listOf((1 to 487), (2 to 1030), (4 to 1321),(8 to 1341), (16 to 1270), (32 to 1456), (64 to 1495))
    //ConcurrencyModel(value4).constructAngPrintModel(500)

    //val value5 = listOf((1 to 477), (2 to 996), (4 to 1275),(8 to 1277), (16 to 1308), (32 to 1398), (64 to 1406))
    //ConcurrencyModel(value5).constructAngPrintModel(500)

    // val value6 = listOf((1 to 487), (2 to 1096), (4 to 1258),(8 to 1342), (16 to 1382), (32 to 1385), (64 to 1519))
    // ConcurrencyModel(value6).constructAngPrintModel(500)

    //val value7 = listOf((1 to 467), (2 to 1055), (4 to 1280),(8 to 1334), (16 to 1332), (32 to 1418), (64 to 1436))
    //ConcurrencyModel(value7).constructAngPrintModel(500)

    val value8 = listOf((1 to 691), (2 to 1454), (4 to 1343),(8 to 1423), (16 to 1294), (32 to 1328), (64 to 1479))
    ConcurrencyModel(value8).constructAngPrintModel(1000)

}