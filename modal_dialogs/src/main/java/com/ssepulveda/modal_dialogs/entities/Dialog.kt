package com.ssepulveda.modal_dialogs.entities

sealed class Dialog {

    data class DialogRegisterAccount(
        val onSuccess: OnClickSuccess, // todo change typeAlias
        val onCancel: OnClickCancel,
    ) : Dialog()

    data class DialogDefault(
        val title: String,
        val description: String,
        val onSuccess: OnClickCancel,
        val onCancel: OnClickCancel,
        val textCancel: String? = null,
        val textSuccess: String? = null,
    ) : Dialog()

}