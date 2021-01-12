package com.ujjwalsingh.kotlinmvvm.di

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

var functionCalls = 0
fun main() {

    GlobalScope.launch { completeMsg() }
    GlobalScope.launch { improved() }
    println("Hello ")
    Thread.sleep(2000)
    println("Count of variable: ${functionCalls}")


//    GlobalScope.launch {
//        delay(2000)
//        print("World")
//    }
//    print("Hello")
//    Thread.sleep(3000)

//    runBlocking {
//       launch(CoroutineName("myCoroutine")) {
//           print("this is from ${this.coroutineContext.get(CoroutineName.Key)}")
//       }
//    }


}
suspend fun completeMsg() {
    delay(500)
    print("World")
    functionCalls++
}

suspend fun improved() {
    delay(1000)
    print("suspend cool")
    functionCalls++
}