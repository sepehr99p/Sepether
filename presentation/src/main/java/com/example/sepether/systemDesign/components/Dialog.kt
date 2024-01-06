package com.example.sepether.systemDesign.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.sepether.R
import com.example.sepether.systemDesign.theme.Primary_Blue

@ExperimentalComposeUiApi
@Composable
fun ShowAlertDialog(
    title: String? = null,
    description: String? = null,
    descriptionColor: Color = Color.Black,
    logoResourceId: Int?,
    buttonTextSize: Int = 14,
    bottomSheetCorners: RoundedCornerShape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp),
    onPositiveButtonClick: (() -> Unit)? = null,
    onNegativeButtonClick: (() -> Unit)? = null,
    positiveButtonTextColor: Color = Color.White,
    negativeButtonTextColor: Color = Primary_Blue,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    positiveButtonCornerShape: RoundedCornerShape = RoundedCornerShape(20.dp),
    negativeButtonCornerShape: RoundedCornerShape = RoundedCornerShape(20.dp),
    bottomSheetVisible: MutableState<Boolean>
) {

    if (bottomSheetVisible.value) {
        Dialog(
            onDismissRequest = {
                bottomSheetVisible.value = false
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = true
            ),
            content = {

                Box(modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            bottomSheetVisible.value = false
                        }
                    }) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .pointerInput(Unit) {
                                detectTapGestures {
                                }
                            },
                        shape = bottomSheetCorners
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.padding(10.dp))

                            logoResourceId?.let {
                                Image(
                                    painter = painterResource(id = it),
                                    contentDescription = "Logo",
                                    modifier = Modifier
                                        .width(68.dp)
                                        .height(68.dp)
                                )
                            }

                            Spacer(modifier = Modifier.padding(15.dp))

                            title?.let {
                                Text(
                                    text = title,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineLarge
                                )

                                Spacer(modifier = Modifier.padding(5.dp))
                            }

                            description?.let {
                                Text(
                                    text = description,
                                    color = descriptionColor,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Spacer(modifier = Modifier.padding(30.dp))
                            }

                            Row {
                                if (onPositiveButtonClick != null && onNegativeButtonClick != null) {

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                    ) {
                                        Button(
                                            onClick = {
                                                onPositiveButtonClick()
                                                bottomSheetVisible.value = false
                                            },
                                            modifier = Modifier
                                                .width(140.dp)
                                                .height(40.dp)
                                                .align(Alignment.Center),
                                            shape = positiveButtonCornerShape,
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Primary_Blue
                                            ),
                                        ) {
                                            positiveButtonText?.let { text ->
                                                Text(
                                                    text = text,
                                                    color = positiveButtonTextColor,
                                                    fontSize = buttonTextSize.sp,
                                                    style = MaterialTheme.typography.headlineSmall
                                                )
                                            }
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)

                                    ) {
                                        Button(
                                            onClick = {
                                                onNegativeButtonClick()
                                                bottomSheetVisible.value = false
                                            },
                                            modifier = Modifier
                                                .width(140.dp)
                                                .height(40.dp)
                                                .align(Alignment.Center),
                                            shape = negativeButtonCornerShape,
                                            border = BorderStroke(1.dp, Primary_Blue),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = colorResource(id = R.color.white)
                                            ),

                                            ) {
                                            negativeButtonText?.let { text ->
                                                Text(
                                                    text = text,
                                                    color = negativeButtonTextColor,
                                                    fontSize = buttonTextSize.sp,
                                                    style = MaterialTheme.typography.headlineSmall
                                                )
                                            }
                                        }
                                    }

                                } else {
                                    onPositiveButtonClick?.let {
                                        Button(
                                            onClick = {
                                                onPositiveButtonClick()
                                                bottomSheetVisible.value = false
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth(0.8f)
                                                .height(40.dp),
                                            shape = positiveButtonCornerShape,
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Primary_Blue
                                            ),
                                        ) {
                                            positiveButtonText?.let { text ->
                                                Text(
                                                    text = text,
                                                    color = positiveButtonTextColor,
                                                    fontSize = buttonTextSize.sp,
                                                    style = MaterialTheme.typography.headlineSmall
                                                )
                                            }
                                        }
                                    }
                                    onNegativeButtonClick?.let {

                                        Button(
                                            onClick = {
                                                onNegativeButtonClick()
                                                bottomSheetVisible.value = false
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth(0.8f)
                                                .height(40.dp),
                                            shape = negativeButtonCornerShape,
                                            border = BorderStroke(1.dp, Primary_Blue),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = colorResource(id = R.color.white)
                                            ),

                                            ) {
                                            negativeButtonText?.let { text ->
                                                Text(
                                                    text = text,
                                                    color = negativeButtonTextColor,
                                                    fontSize = buttonTextSize.sp,
                                                    style = MaterialTheme.typography.headlineSmall
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                        }

                    }
                }
            })
    }
}

@ExperimentalComposeUiApi
@Composable
fun ShowErrorDialog(
    visibility: MutableState<Boolean> = mutableStateOf(true),
    title: String = stringResource(id = R.string.error),
    description: String
) {
    ShowAlertDialog(
        bottomSheetVisible = visibility,
        logoResourceId = R.drawable.ic_error,
        title = title,
        description = description,
        positiveButtonText = stringResource(R.string.close),
        onPositiveButtonClick = {}
    )
}
