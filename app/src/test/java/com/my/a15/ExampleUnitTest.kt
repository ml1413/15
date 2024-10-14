package com.my.a15

import com.my.a15.FifteenEngine.Companion.getInitialState
import com.my.a15.FifteenEngine.Companion.isWin
import com.my.a15.FifteenEngine.Companion.transitionState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun swapElement() {
        val cell = 1
        val list = listOf(cell, null)
        val expected = listOf(null, cell)
        val newState = transitionState(oldState = list, cell = cell)
        assertEquals(expected, newState)
    }

    @Test
    fun IsWin() {
        val list = listOf(1, 2, 3, 4, null)
        assertTrue(isWin(state = list))
    }

    @Test
    fun getInitialStateTest() {
        val instance1 = getInitialState()
        val instance2 = getInitialState()
        assertTrue(instance1.isNotEmpty())
        assertTrue(instance1.contains(null))
        assertEquals(16, instance1.size)
        assertTrue(instance1.any() { it is Int })
        assertNotEquals(instance1, instance2)
    }

    @Test
    fun transitionStateTest() {
        val list = (1..14) + null + 15
        val expected = (1..15) + null
        val result = transitionState(oldState = list, cell = 15)
        assertEquals(expected, result)
        val resultNot = transitionState(oldState = list, cell = 13)
        assertEquals(list, resultNot)
    }
}