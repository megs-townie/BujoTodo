package com.example.composecalculator

sealed class CalculatorOperation(val symbol: String) {
    object Add : CalculatorOperation(symbol = "+")
    object Subtract : CalculatorOperation(symbol = "-")
    object Multiply : CalculatorOperation(symbol = "x")
    object Divide : CalculatorOperation(symbol = "/")
}
