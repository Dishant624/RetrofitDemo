package com.example.retrofitdemo.kotlin_demo

fun printMessage(message:String):Unit{
    println(message)
}

fun printMessageWithPrefix(message: String,prefix: String ="Info"){
    println("[$prefix] $message")
}

fun sum(x:Int ,y:Int):Int{
    return x+y;
}

fun multiply(x:Int, y:Int) = x*y

fun main(){
    printMessage("Hello Dishant Patel")
    printMessageWithPrefix("hello","log")
    printMessageWithPrefix("hello")
    println(sum(10,20))
    println(multiply(10,20))

}