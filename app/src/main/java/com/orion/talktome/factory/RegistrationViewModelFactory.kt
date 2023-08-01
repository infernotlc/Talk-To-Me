package com.orion.talktome.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orion.talktome.composeui.Register.viewModel.RegisterViewModel
import com.genband.mobile.ServiceProvider

//ViewModelProvider.Factory, ViewModel örneklerini oluşturmak için kullanılan bir arayüzdür. ViewModel sınıflarının parametreleri veya bağımlılıkları olduğunda, ViewModelProvider.Factory kullanılarak bu bağımlılıklar ViewModel'lere aktarılabilir.

class RegistrationViewModelFactory(private val serviceProvider: ServiceProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(serviceProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}