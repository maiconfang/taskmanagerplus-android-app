package maif.taskmanagerplus.espresso

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.rules.ActivityScenarioRule
import maif.taskmanagerplus.R
import maif.taskmanagerplus.ui.login.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.allOf
import org.hamcrest.CoreMatchers.not


//@RunWith(AndroidJUnit4::class)
class TaskManagerTest {

    // Launches the LoginActivity before each test
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)



    @Test
    fun test() {
        println("Hello World - Maicon Fang TaskManagerTest")

    }


    @Test
    fun testFailOnPurpose() {
        // Purpose: This test is designed to fail intentionally to demonstrate a failed test case in the report.

        // Trying to match a view that should display the text "Non-existent text"
        // Since this text does not exist, this check will fail
//        onView(withId(R.id.text_home)) // Adjust to an ID available on the login screen or main screen
//            .check(matches(withText("Non-existent text")))
    }

//    @Test
    fun testCreateNewTask() {

        // Perform login
        performLogin()

        // Navigate to "Task Manager"
        navigateToTaskManager()

        // Add a new task
        addTask("Study Kotlin", "Study about coroutines")

        // Verify if the task "Study Kotlin" appears in the task RecyclerView
        onView(withId(R.id.rv_task_list))
            .check(matches(hasDescendant(withText("Study Kotlin"))))
    }

//    @Test
    fun testFilterPendingTasks() {

        // Perform login
        performLogin()

        // Navigate to "Task Manager"
        navigateToTaskManager()

        // Add a new task
        addTask("Complete project report", "Finalize and submit the project report by Friday")

        onView(withId(R.id.et_search_task)).perform(typeText("Complete project report"), closeSoftKeyboard())

        // Verify if the task "Buy groceries for the week" with the status "Pending" is displayed
        onView(withId(R.id.rv_task_list))
            .check(matches(hasDescendant(allOf(
                withText("Complete project report"), // Verify the task title
                hasSibling(withText("Pending")) // Verify if the status "Pending" is on the same hierarchy level
            ))))
    }

//    @Test
    fun testEditExistingTaskWithNewTask() {

        // Perform login
        performLogin()

        // Navigate to "Task Manager"
        navigateToTaskManager()

        // Add a new task to be edited
        addTask("Clean the car", "Clean the car inside and outside")

        onView(withId(R.id.et_search_task)).perform(typeText("Clean the car"), closeSoftKeyboard())

        // Verify if the task "Clean the car" appears in the task RecyclerView
        onView(withId(R.id.rv_task_list))
            .check(matches(hasDescendant(withText("Clean the car"))))

        // Click the "Edit" button of the newly created task
        onView(allOf(withId(R.id.btn_edit_task), hasSibling(withText("Clean the car"))))
            .perform(click())

        // Edit the task title and description
        onView(withId(R.id.et_edit_task_title)).perform(clearText(), typeText("Clean the car completely"), closeSoftKeyboard())
        onView(withId(R.id.et_edit_task_description)).perform(clearText(), typeText("Clean the car, including the interior and exterior"), closeSoftKeyboard())

        // Click the "Save" button
        onView(withId(R.id.btn_save_task)).perform(click())

        // Verify if the task has been updated correctly in the list
        onView(withId(R.id.rv_task_list))
            .check(matches(hasDescendant(withText("Clean the car completely"))))
    }

//    @Test
    fun testViewTaskDetails() {

        // Perform login
        performLogin()

        // Navigate to "Task Manager"
        navigateToTaskManager()

        // Add a new task
        addTask("Study Espresso", "Learn how to write UI tests with Espresso")

        // Verify if the task "Study Espresso" appears in the task RecyclerView
        onView(withId(R.id.rv_task_list))
            .check(matches(hasDescendant(withText("Study Espresso"))))

        onView(withId(R.id.et_search_task)).perform(typeText("Study Espresso"), closeSoftKeyboard())

        // Click the CardView containing the task title
        onView(allOf(withId(R.id.tv_task_title), withText("Study Espresso"))).perform(click())

        // Verify the details of the newly created task
        viewTaskDetails("Study Espresso", "Learn how to write UI tests with Espresso", "Pending")
    }

//    @Test
    fun testDeleteNewTask() {

        // Perform login
        performLogin()

        // Navigate to "Task Manager"
        navigateToTaskManager()

        // Add a new task that does not yet exist
        addTask("Plan the weekend trip", "Organize the itinerary and book hotels")

        onView(withId(R.id.et_search_task)).perform(typeText("Plan the weekend trip"), closeSoftKeyboard())

        // Click the "Delete" button of the task "Plan the weekend trip"
        onView(allOf(withId(R.id.btn_delete_task), hasSibling(withText("Plan the weekend trip"))))
            .perform(click())

        // Confirm the deletion in the dialog box
        onView(withText("DELETE")).perform(click())

        // Confirm that the task "Plan the weekend trip" no longer appears in the list
        onView(withId(R.id.rv_task_list))
            .check(matches(not(hasDescendant(withText("Plan the weekend trip")))))
    }

//    @Test
    fun testFilterTasksByCompletedStatus() {

        // Log in
        performLogin()

        // Navigate to the "Task Manager"
        navigateToTaskManager()


        // Click the "Add Task" button
        onView(withId(R.id.btn_add_task)).perform(click())

        // Insert the title and description passed as parameters
        onView(withId(R.id.et_task_title)).perform(typeText("Buy birthday gift"), closeSoftKeyboard())
        onView(withId(R.id.et_task_description)).perform(typeText("Choose and purchase a gift for Luana's birthday"), closeSoftKeyboard())
        onView(withId(R.id.cb_task_completed)).perform(click())

        // Click the "Save" button
        onView(withId(R.id.btn_save_task)).perform(click())

        // Select the "Completed" filter option
        onView(withId(R.id.cb_completed)).perform(click())

        onView(withId(R.id.et_search_task)).perform(typeText("Buy birthday gift"), closeSoftKeyboard())

        // Verify that only completed tasks are displayed
        onView(withId(R.id.rv_task_list))
            .check(matches(hasDescendant(allOf(
                withText("Buy birthday gift"), // Verify the task title
                hasSibling(withText("Completed")) // Verify if the status "Pending" is on the same hierarchy level
            ))))
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

    // Function to verify the task details
    private fun viewTaskDetails(title: String, description: String, status: String) {

        // Verify if the task details are displayed correctly
        onView(withId(R.id.tv_task_detail_title)).check(matches(withText(title)))
        onView(withId(R.id.tv_task_detail_description)).check(matches(withText(description)))
        onView(withId(R.id.tv_task_detail_status)).check(matches(withText(status)))
    }


}
