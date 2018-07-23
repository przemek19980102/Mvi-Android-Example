package com.example.appName.presentation.random

sealed class RandomPartialState {
    data class FirstRolled(val generatedNumber: Int) : RandomPartialState()
    data class SecondRolled(val generatedNumber: Int) : RandomPartialState()
}
