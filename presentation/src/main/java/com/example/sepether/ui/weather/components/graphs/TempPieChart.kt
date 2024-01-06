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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet


@Composable
fun TempPieChart(
    forecastInfo: ForecastInfo?
) {
    forecastInfo?.let {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .height(180.dp),
            factory = { context ->

                val entries: List<PieEntry> =
                    forecastInfo.time.zip(forecastInfo.maxTemperatures) { x, y ->
                        PieEntry(
                            timeStamp(x).toFloat(),
                            y.toFloat()
                        )
                    }
                PieChart(context).config(genDataSet(entries))
            }
        )
    }
}


fun genDataSet(entries: List<PieEntry>): PieDataSet {
    return PieDataSet(entries,"label")
}
fun PieChart.config(dataSet: PieDataSet): PieChart = this.apply {
    data = PieData(dataSet)

    setNoDataText("No data :(")

    dragDecelerationFrictionCoef = 0.9f
    setTouchEnabled(false)
    description.isEnabled = false
    legend.isEnabled = false
    // Refresh and return the chart
    invalidate()
}