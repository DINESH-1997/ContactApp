package com.dinesh.contactbook.navigation

import kotlinx.serialization.Serializable

sealed class Screens {
    @Serializable
    data object Contacts: Screens()
}