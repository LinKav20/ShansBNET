package com.github.linkav20.bnets.di

import android.content.Context
import com.github.linkav20.bnets.services.InternetNetworkInformant
import com.github.linkav20.bnets.services.impl.NetworkConnectivityObserver
import com.github.linkav20.core_network.BuildConfig
import com.github.linkav20.core_network.api.MyApi
import dagger.*
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun internetConnectivityObserver(): InternetNetworkInformant

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
}

@Module
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun internetConnectivity(manager: NetworkConnectivityObserver): InternetNetworkInformant
}


