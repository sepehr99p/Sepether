package com.example.sepether.utils

import java.text.SimpleDateFormat

fun dayOfWeek(time: String): String {
    return SimpleDateFormat(Constants.TIME_PATTERN_DAY_OF_WEEK).format(SimpleDateFormat(Constants.TIME_PATTERN_DAY_WEEK).parse(time))
}