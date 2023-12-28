package com.example.sepether.ui.weather.components.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.domain.entities.ForecastInfo
import com.example.sepether.utils.timeStamp
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


@Composable
fun LineGraph(
    forecastInfo: ForecastInfo?
) {
    forecastInfo?.let {
        AndroidView(
            modifier = Modifier.fillMaxSize().height(180.dp),
            factory = { context ->
                val chart = LineChart(context)
                val entries: List<Entry> =
                    forecastInfo.time.zip(forecastInfo.maxTemperatures) { x, y -> Entry(timeStamp(x).toFloat(), y.toFloat()) }  // Convert the x and y data into entries
                val dataSet = LineDataSet(entries, "dataLabel").apply {
//                    color = lineColor.toArgb()
                    setDrawValues(true)
//                    setDrawCircles(drawMarkers)
                    setDrawFilled(true)
//                    fillColor = fillColor.toArgb()
                    fillAlpha = fillAlpha
                }


                chart.data = LineData(dataSet)


                chart.setTouchEnabled(false)
                chart.isDragEnabled = false
                chart.isScaleXEnabled = false
                chart.isScaleYEnabled = false

                chart.description.isEnabled = false
                chart.legend.isEnabled = false

//                chart.axisLeft.textColor = axisTextColor.toArgb()
                chart.axisRight.isEnabled = false
//                chart.xAxis.textColor = axisTextColor.toArgb()
//                chart.xAxis.position = xAxisPosition

                // Refresh and return the chart
                chart.invalidate()
                chart
            }
        )
    }
}