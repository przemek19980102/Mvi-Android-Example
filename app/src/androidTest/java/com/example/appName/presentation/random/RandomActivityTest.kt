package com.example.appName.presentation.random

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.example.appName.R
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RandomActivityTest {
    private val viewStateSubject = BehaviorSubject.create<RandomViewState>()

    @get:Rule
    val activityTestRule: ActivityTestRule<RandomActivity> = ActivityTestRule<RandomActivity>(RandomActivity::class.java, true, true)

    @Before
    fun intentWithStubbedNoteId() {
        activityTestRule.activity.apply {
            presenter = object : RandomPresenter {
                override fun getCurrentViewState(): RandomViewState? = viewStateSubject.value

                override fun dispose() {}

                override fun getStateObservable(): Observable<RandomViewState> = viewStateSubject

            }

            subscribeToViewState()
        }
    }

    @Test
    fun whenViewStateWithFirstNumberIsEmittedFirstNumberViewIsSetToItsValue() {
        viewStateSubject.onNext(RandomViewState(1, 2))

        onView(withId(R.id.randomFirstNumber)).check(matches(withText("1")))
    }

    @Test
    fun whenViewStateWithSecondNumberIsEmittedSecondNumberViewIsSetToItsValue() {
        viewStateSubject.onNext(RandomViewState(1, 2))

        onView(withId(R.id.randomSecondNumber)).check(matches(withText("2")))
    }
}