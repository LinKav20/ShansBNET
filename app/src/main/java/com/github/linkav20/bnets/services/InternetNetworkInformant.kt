package com.github.linkav20.bnets.services

import kotlinx.coroutines.flow.Flow

interface InternetNetworkInformant {

    fun observe() : Flow<NetworkStatus>

}

enum class NetworkStatus {
    CONNECTED,
    DISCONNECTED;
}