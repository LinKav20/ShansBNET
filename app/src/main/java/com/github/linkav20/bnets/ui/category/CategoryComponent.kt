package com.github.linkav20.bnets.ui.category

import androidx.lifecycle.ViewModel
import com.github.linkav20.bnets.DI
import com.github.linkav20.bnets.di.ScreenScope
import com.github.linkav20.bnets.di.ViewModelFactory
import com.github.linkav20.bnets.di.ViewModelKey
import com.github.linkav20.bnets.services.InternetNetworkInformant
import com.github.linkav20.bnets.services.impl.NetworkConnectivityObserver
import com.github.linkav20.core_network.api.MyApi
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap


@Component(modules = [CategoryModule::class])
@ScreenScope
interface CategoryComponent {
    fun viewModelFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun api(api: MyApi): Builder

        @BindsInstance
        fun internetConnectivityObserver(internetNetworkInformant: InternetNetworkInformant): Builder

        fun build(): CategoryComponent
    }


    companion object {
        fun create() = with(DI.appComponent) {
            DaggerCategoryComponent.builder()
                .internetConnectivityObserver(DI.appComponent.internetConnectivityObserver())
                .api(DI.networkComponent.api()).build()
        }
    }
}

@Module
abstract class CategoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun categoryViewModel(viewModel: CategoryViewModel): ViewModel
}