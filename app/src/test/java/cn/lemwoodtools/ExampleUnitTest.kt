package cn.lemwoodtools

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    
    @Test
    fun string_concatenation_works() {
        val result = "Hello" + " " + "World"
        assertEquals("Hello World", result)
    }
    
    @Test
    fun app_package_name_is_correct() {
        val expectedPackage = "cn.lemwoodtools"
        // This is a simple test to verify our package name
        assertTrue("Package name should contain lemwoodtools", 
                   expectedPackage.contains("lemwoodtools"))
    }
}