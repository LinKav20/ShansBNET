package com.github.linkav20.bnets.services.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.github.linkav20.bnets.services.InternetNetworkInformant
import com.github.linkav20.bnets.services.NetworkStatus
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkConnectivityObserver @Inject constructor(
    private val context: Context
) : InternetNetworkInformant {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<NetworkStatus> =
        callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(NetworkStatus.CONNECTED) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(NetworkStatus.DISCONNECTED) }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
}