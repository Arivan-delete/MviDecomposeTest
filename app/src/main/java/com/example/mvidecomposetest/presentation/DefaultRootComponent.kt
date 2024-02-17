package com.example.mvidecomposetest.presentation

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.mvidecomposetest.domain.Contact
import kotlinx.parcelize.Parcelize

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    val stack: Value<ChildStack<Config, ComponentContext>> = childStack(
        source = navigation,
        initialConfiguration = Config.ContactList,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): ComponentContext {
        return when (config) {
            Config.AddContact -> {
                DefaultAddContactComponent(
                    componentContext = componentContext,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
            }

            Config.ContactList -> {
                DefaultContactListComponent(
                    componentContext = componentContext,
                    onAddContactRequest = {
                        navigation.push(Config.AddContact)
                    },
                    onEditingContactRequest = {
                        navigation.push(Config.EditContact(it))
                    }
                )
            }

            is Config.EditContact -> {
                DefaultEditContactComponent(
                    componentContext = componentContext,
                    contact = config.contact,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
            }
        }
    }

    sealed interface Config : Parcelable {

        @Parcelize
        data object ContactList : Config

        @Parcelize
        data object AddContact : Config

        @Parcelize
        data class EditContact(val contact: Contact) : Config
    }
}