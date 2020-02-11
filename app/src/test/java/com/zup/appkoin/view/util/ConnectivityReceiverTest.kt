package com.zup.appkoin.view.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ConnectivityReceiverTest {

    @InjectMockKs
    lateinit var connectivityReceiver: ConnectivityReceiver

    @RelaxedMockK
    lateinit var context: Context

    @RelaxedMockK
    lateinit var connectivityManager: ConnectivityManager

    @RelaxedMockK
    lateinit var connectivityReceiverListener: ConnectivityReceiver.ConnectivityReceiverListener

    @RelaxedMockK
    lateinit var intent: Intent


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ConnectivityReceiver.connectivityReceiverListener = connectivityReceiverListener
    }

    @After
    fun tearDown() {

    }

    @Test
    fun isConnectedToNetworkTrueTest() {
        // GIVEN  = dado que tal coisa
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetworkInfo?.isConnected } returns true
        // WHEN = quando
        connectivityReceiver.isConnectedToNetwork(context)

        // THEN =  entao
        Assert.assertTrue(true)
    }

    @Test
    fun isConnectedToNetworkFalseTest() {
        // GIVEN  = dado que tal coisa
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetworkInfo?.isConnected } returns false
        // WHEN = quando
         connectivityReceiver.isConnectedToNetwork(context)

        // THEN =  entao
        Assert.assertFalse(false)
    }

    @Test
    fun onReceive() {
        // GIVEN  = dado que tal coisa
        every { connectivityReceiver.isConnectedToNetwork(context) } returns true
        // WHEN = quando
        connectivityReceiver.onReceive(context, intent)

        // THEN =  entao
        verify {
            ConnectivityReceiver.connectivityReceiverListener?.onNetworkConnectionChanged(
                true
            )
        }
    }
}

