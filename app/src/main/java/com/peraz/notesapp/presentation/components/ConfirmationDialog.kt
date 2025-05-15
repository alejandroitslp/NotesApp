package com.peraz.notesapp.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    dismissButton: () -> Unit,
    confirmButton: () -> Unit
) {
    AlertDialog(onDismissRequest = dismissButton ,
        dismissButton = {
            Button(onClick =  dismissButton ) {
                Text(text = "Mejor no")
            }
    },
        confirmButton = {
            Button(onClick =  confirmButton , colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )) {
                Text(text = "Si a la brg")
            }
        },
        title = {
            Text(text = "Deseas eliminar esta nota?", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        },
        text = {
            Text(text = "Esto borrara de forma permanente la nota")
        })
}

@Preview(showBackground = true)
@Composable
fun ConfirmationDialogPreview(
){
    ConfirmationDialog(
        confirmButton = {},
        dismissButton = {}
    )
}