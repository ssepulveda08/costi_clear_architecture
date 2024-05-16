package com.ssepulveda.modal_dialogs.entities

sealed class Dialog {

    data class DialogDefault(
        val title: String,
        val description: String,
        val onCancel: () -> Unit,
        val onSuccess: () -> Unit,
    ) : Dialog()

}