package com.github.linkav20.bnets.ui.product

import androidx.lifecycle.ViewModel
import com.github.linkav20.bnets.DI
import com.github.linkav20.bnets.di.ScreenScope
import com.github.linkav20.bnets.di.ViewModelFactory
import com.github.linkav20.bnets.di.ViewModelKey
import com.github.linkav20.bnets.services.InternetNetworkInformant
import com.github.linkav20.core_network.api.MyApi
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [ProductModule::class])
@ScreenScope
interface ProductComponent {
    fun viewModelFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun api(api: MyApi): Builder

        @BindsInstance
        fun internetConnectivityObserver(internetNetworkInformant: InternetNetworkInformant): Builder

        fun build(): ProductComponent
    }

    companion object{
        fun create() = with(DI.appComponent){
            DaggerProductComponent.builder()
                .internetConnectivityObserver(DI.appComponent.internetConnectivityObserver())
                .api(DI.networkComponent.api()).build()
        }
    }
}

@Module
abstract class ProductModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun productViewModel(viewModel: ProductViewModel): ViewModel
}