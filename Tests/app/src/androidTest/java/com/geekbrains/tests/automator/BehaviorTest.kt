package com.geekbrains.tests.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.uiautomator.UiDevice
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    private val uiDevice: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun test_OpenDetailsScreen() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "ggggg"
        val searchButton: UiObject2 = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton.click()

        val toDetails: UiObject2 = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        val textSearchActivity = uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT).text
        toDetails.click()

        val textDetailsActivity =
            uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT).text
        Assert.assertEquals(textDetailsActivity, textSearchActivity)
    }

    @Test
    fun test_EmptyRequest() {
        val searchButton: UiObject2 = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton.click()
        uiDevice.wait(Until.findObject(By.clazz("android.widget.Toast")), TIMEOUT)
    }

    @Test
    fun test_IncorrectInput() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "%%%%%%%%%"
        val searchButton: UiObject2 = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton.click()
        val text = uiDevice.findObject(By.res(packageName, "totalCountTextView")).text
        Assert.assertEquals("Number of results: 0", text)
    }

    @Test
    fun test_DetailsScreenIncrement() {
        val toDetails: UiObject2 = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)
        val incrementButton: UiObject2 = uiDevice.findObject(By.res(packageName, "incrementButton"))
        incrementButton.click()
        val text = uiDevice.findObject(By.res(packageName, "totalCountTextView")).text
        Assert.assertEquals("Number of results: 1", text)
    }

    @Test
    fun test_DetailsScreenDecrement() {
        val toDetails: UiObject2 = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)
        val decrementButton: UiObject2 = uiDevice.findObject(By.res(packageName, "decrementButton"))
        decrementButton.click()
        val text = uiDevice.findObject(By.res(packageName, "totalCountTextView")).text
        Assert.assertEquals("Number of results: -1", text)
    }

    companion object {
        private const val TIMEOUT = 5000L
    }
}