package com.example.appName.presentation.simpleActivity

import java.io.Serializable

data class SimpleViewState(val firstRoll: Int = 0,
                           val secondRoll: Int = 0) : Serializable {
    constructor(previous: SimpleViewState,
                firstRoll: Int = previous.firstRoll,
                secondRoll: Int = previous.secondRoll) : this(firstRoll, secondRoll)
}
