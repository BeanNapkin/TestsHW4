package com.geekbrains.tests

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.details.DetailsActivity
import com.geekbrains.tests.view.search.MainActivity
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activitySearch_IsWorking() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())

        if (BuildConfig.TYPE == MainActivity.FAKE) {
            onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 42")))
        } else {
            onView(isRoot()).perform(delay())
            onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 2283")))
        }
    }

    private fun delay(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $2 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(2000)
            }
        }
    }

    @Rule
    @JvmField
    val mainActivityRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun testSearchEditText(){
        val id = R.id.searchEditText
        val hint = "Enter keyword e.g. android"
        val assertion: ViewAssertion = matches(withHint(hint))

        onView(withId(id)).check(matches(isDisplayed()))
        onView(withId(id)).check(matches(isClickable()))
        onView(withId(id)).check(assertion)

        onView(withId(id)).check(matches(hasImeAction(EditorInfo.IME_ACTION_SEARCH)))
    }

    @Test
    fun testToDetailsButton(){
        val id = R.id.toDetailsActivityButton

        onView(withId(id)).check(matches(isDisplayed()))
        onView(withId(id)).check(matches(isClickable()))

        val buttonText = "to details"
        val assertion: ViewAssertion = matches(withText(buttonText))
        onView(withId(id)).check(assertion)

        onView(withId(id)).perform(click())
        intended(hasComponent(DetailsActivity::class.java.getName()))
    }

    @After
    fun close() {
        scenario.close()
    }
}
