package com.example.sepether.ui.weather.components.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
//                    color = Color.Transparent.toArgb()

                    setDrawValues(true)
//                    setDrawCircles(drawMarkers)
                    setDrawFilled(true)
                    fillColor = Color.Transparent.toArgb()
                    fillColor = Color.Transparent.toArgb()
                    fillAlpha = Color.Transparent.toArgb()
                }


                chart.apply {
                    data = LineData(dataSet)

                    setGridBackgroundColor(Color.Transparent.toArgb())
                    setNoDataText("No data :(")

                    dragDecelerationFrictionCoef = 0.9f
                    setTouchEnabled(false)
                    isDragEnabled = true
                    isScaleXEnabled = false
                    isScaleYEnabled = false

                    description.isEnabled = false
                    legend.isEnabled = false

                    xAxis.textColor = Color.Transparent.toArgb()

                    axisRight.isEnabled = false

                    // Refresh and return the chart
                    invalidate()
                }
            }
        )
    }
}