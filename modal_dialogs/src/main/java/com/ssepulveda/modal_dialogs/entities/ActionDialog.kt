package com.ssepulveda.modal_dialogs.entities

sealed class ActionDialog {
    data object Default :ActionDialog()
    data class AddAccount(val data: AddAccountForm): ActionDialog()
}