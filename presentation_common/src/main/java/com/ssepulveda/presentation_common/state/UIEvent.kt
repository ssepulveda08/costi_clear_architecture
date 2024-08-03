package com.ssepulveda.presentation_common.state

import com.ssepulveda.modal_dialogs.entities.Dialog

sealed class UIEvent {

    data class ShowDialog(val dialog: Dialog) : UIEvent()

    data class ShowSnackBar(val message: String) : UIEvent()
}