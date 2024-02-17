package com.example.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Store

interface AddContactStore :
    Store<AddContactStore.Intent, AddContactStore.State, AddContactStore.Label> {

    data class State(
        val username: String,
        val phone: String
    )

    sealed interface Label {

        data object ContactSaved : Label
    }

    sealed interface Intent {

        data class ChangeUsername(val username: String) : Intent
        data class ChangePhone(val phone: String) : Intent

        data object SaveContact : Intent
    }
}