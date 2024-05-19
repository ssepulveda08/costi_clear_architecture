package com.ssepulveda.modal_dialogs.entities

sealed class Dialog {

    data class DialogDefault(
        val title: String,
        val description: String,
        val onSuccess: () -> Unit,
        val onCancel: () -> Unit,
        val textCancel: String? = null,
        val textSuccess: String? = null,
    ) : Dialog()

}