package maif.taskmanagerplus.espresso

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import maif.taskmanagerplus.R
import maif.taskmanagerplus.ui.login.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.hamcrest.Matcher

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    // Launches the LoginActivity before each test
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    //    @Ignore // Skip this test
    @Test
    fun testLoginFieldsInteraction() {
        // Type an email address in the username field
        onView(withId(R.id.username))
            .perform(typeText("test@example.com"), closeSoftKeyboard())

        // Type a password in the password field
        onView(withId(R.id.password))
            .perform(typeText("password123"), closeSoftKeyboard())

        // Click the login button
        onView(withId(R.id.login)).perform(click())

        // Use ActivityScenario to access the Toast message and validate
        activityRule.scenario.onActivity { activity ->
            val toastMessage = activity.getLastToastMessage()
            val expectedMessage = "Welcome ! test@example.com"

            // Log the captured message
            Log.d("LoginActivityTest", "Captured Toast message: $toastMessage")

            // Validate the Toast message
            assertEquals("The Toast message should match the expected message.", expectedMessage, toastMessage)
        }
    }

    @Test
    fun testCapturePasswordErrorMessage() {

        // Type an email address in the username field
        onView(withId(R.id.username))
            .perform(typeText("test@example.com"), closeSoftKeyboard())

        // Type a short password that is less than 3 characters
        onView(withId(R.id.password))
            .perform(typeText("y"), closeSoftKeyboard())

        // Use the custom ViewAction to capture the error text
        onView(withId(R.id.password)).perform(getErrorText())

        // Use the captured error message
        println("Captured error message: $capturedError")

        // Optionally, assert that the captured error message matches expected text
        assertEquals("Password must be >2 characters", capturedError)
    }


    // Variable to hold the captured error message
    private var capturedError: String? = null

    // Custom ViewAction to capture error text from EditText
    private fun getErrorText(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(EditText::class.java)
            }

            override fun getDescription(): String {
                return "Get error text from EditText"
            }

            override fun perform(uiController: UiController, view: View) {
                val editText = view as EditText
                capturedError = editText.error?.toString() // Capture the error text
            }
        }
    }






}
