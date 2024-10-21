package maif.taskmanagerplus.appium

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

class AppiumLoginActivityTest {

    private lateinit var driver: AppiumDriver<MobileElement>

    @Before
    fun setUp() {
        val capabilities = DesiredCapabilities().apply {
            setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
            setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator")
            setCapability(MobileCapabilityType.APP_PACKAGE, "maif.taskmanagerplus")
            setCapability(MobileCapabilityType.APP_ACTIVITY, "maif.taskmanagerplus.ui.login.LoginActivity")
        }

        driver = AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
    }

    @Test
    fun testLoginFieldsInteraction() {
        // Digita o email no campo de usuário
        val emailField = driver.findElementById("maif.taskmanagerplus:id/username")
        emailField.sendKeys("test@example.com")

        // Digita a senha no campo de senha
        val passwordField = driver.findElementById("maif.taskmanagerplus:id/password")
        passwordField.sendKeys("password123")

        // Clica no botão de login
        val loginButton = driver.findElementById("maif.taskmanagerplus:id/login")
        loginButton.click()

        // Aguarda a tela de boas-vindas ou outra confirmação
        val toastMessage = driver.findElementByXPath("//android.widget.Toast[1]").text
        assertEquals("Welcome ! test@example.com", toastMessage)
    }

    @Test
    fun testCapturePasswordErrorMessage() {
        // Digita o email no campo de usuário
        val emailField = driver.findElementById("maif.taskmanagerplus:id/username")
        emailField.sendKeys("test@example.com")

        // Digita uma senha curta para gerar um erro
        val passwordField = driver.findElementById("maif.taskmanagerplus:id/password")
        passwordField.sendKeys("y")

        // Verifica a mensagem de erro exibida
        val errorMessage = passwordField.getAttribute("error")
        println("Captured error message: $errorMessage")
        assertEquals("Password must be >2 characters", errorMessage)
    }

    @After
    fun tearDown() {
        driver.quit()
    }
}
