package com.even.testHelper

import com.even.domaine.entité.UnCoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

open class CouroutineTestHelper : GeneralTestHelper() {

    protected val coroutineProvider = UnCoroutineDispatcher()
    protected val SubstitutFilPrincipal = newSingleThreadContext("UI thread")
    protected val delaiPourWithContext: Long = 15

    @Before
    open fun setUp() {
        Dispatchers.setMain(SubstitutFilPrincipal)
    }

    @After
    open fun tearDown() {
        Dispatchers.resetMain()
        SubstitutFilPrincipal.close()
    }
}