package com.github.linkav20.bnets

import android.app.Application
import com.github.linkav20.bnets.di.DaggerAppComponent
import com.github.linkav20.core_network.di.DaggerNetworkComponent

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        DI.appComponent = DaggerAppComponent.builder().appContext(this).build()
        DI.networkComponent = DaggerNetworkComponent.create()
    }
}