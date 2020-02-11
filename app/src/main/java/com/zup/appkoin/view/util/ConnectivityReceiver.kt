package com.zup.appkoin.view.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, arg1: Intent) {
        connectivityReceiverListener?.onNetworkConnectionChanged(isConnectedToNetwork(context))
    }

    fun isConnectedToNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnected ?: false
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }
}