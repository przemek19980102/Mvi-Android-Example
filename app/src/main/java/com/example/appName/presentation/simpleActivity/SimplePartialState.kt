package com.example.appName.presentation.simpleActivity

sealed class SimplePartialState {
    data class FirstRolled(val generatedNumber: Int) : SimplePartialState()
    data class SecondRolled(val generatedNumber: Int) : SimplePartialState()
}
