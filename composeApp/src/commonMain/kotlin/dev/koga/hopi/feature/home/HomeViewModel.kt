package dev.koga.hopi.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.koga.hopi.repository.MmoRepository
import dev.koga.hopi.feature.details.GameDetailsViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val module = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::GameDetailsViewModel)
}

class HomeViewModel(
    repository: MmoRepository
) : ViewModel() {

    val games = repository.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

}