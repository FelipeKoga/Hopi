package dev.koga.mmofinder.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.koga.mmofinder.MmoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val module = module {
    viewModelOf(::HomeViewModel)
}

class HomeViewModel(
    private val repository: MmoRepository
) : ViewModel() {

    val games = repository.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

}