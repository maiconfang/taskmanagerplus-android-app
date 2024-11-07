package maif.taskmanagerplus


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class TaskManagerTestMaicon {



    @Test
    fun testMaicon() {
        println("Hello World - Maicon Fang")

    }


    @Test
    fun testCreateNewTask() {

        println("Hello World - Maicon Fang baaaa")
    }

    // Helper method to navigate to "Task Manager"
    private fun navigateToTaskManager() {
        // Open the menu
        onView(withContentDescription("Open navigation drawer")) // Use the content description of the menu icon
            .perform(click())

        // Click on "Task Manager"
        onView(withText("Task Manager"))
            .perform(click())

        // Verify if it is on the "Task Manager" screen
        onView(withId(R.id.rv_task_list)) // Ensure the RecyclerView of tasks is present
            .check(matches(isDisplayed()))
    }


    // Helper method to perform login
    private fun performLogin() {
        onView(withId(R.id.username))
            .perform(typeText("user@taskmanagerplus.com"), closeSoftKeyboard())
        onView(withId(R.id.password))
            .perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.login)).perform(click())

        // Verify if it is on the Home screen
        onView(withId(R.id.text_home)) // Replace with a unique element from Home to validate it's there
            .check(matches(isDisplayed()))
    }

    // Function to add a new task
    private fun addTask(title: String, description: String) {
        // Click the "Add Task" button
        onView(withId(R.id.btn_add_task)).perform(click())

        // Insert the title and description passed as parameters
        onView(withId(R.id.et_task_title)).perform(typeText(title), closeSoftKeyboard())
        onView(withId(R.id.et_task_description)).perform(typeText(description), closeSoftKeyboard())

        // Click the "Save" button
        onView(withId(R.id.btn_save_task)).perform(click())
    }


}
