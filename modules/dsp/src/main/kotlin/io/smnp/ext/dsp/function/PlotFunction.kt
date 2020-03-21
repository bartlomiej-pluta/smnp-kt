package io.smnp.ext.dsp.function

import io.smnp.callable.function.Function
import io.smnp.callable.function.FunctionDefinitionTool
import io.smnp.callable.signature.Signature.Companion.vararg
import io.smnp.error.CustomException
import io.smnp.type.enumeration.DataType.*
import io.smnp.type.matcher.Matcher.Companion.anyType
import io.smnp.type.matcher.Matcher.Companion.listOf
import io.smnp.type.matcher.Matcher.Companion.mapOfMatchers
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.model.Value
import io.smnp.util.config.ConfigMapSchema
import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.BitmapEncoder.BitmapFormat
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.VectorGraphicsEncoder
import org.knowm.xchart.VectorGraphicsEncoder.VectorGraphicsFormat
import org.knowm.xchart.XYChart
import org.knowm.xchart.style.markers.SeriesMarkers

class PlotFunction : Function("plot") {
   private val chartSchema = ConfigMapSchema()
      .optional("title", ofType(STRING))
      .optional("output", ofType(STRING))
      .optional("format", ofType(STRING), Value.string("png"))

   private val signalSchema = ConfigMapSchema()
      .required("name", ofType(STRING))
      .required("y", listOf(INT, FLOAT))
      .optional("x", listOf(INT, FLOAT))

   override fun define(new: FunctionDefinitionTool) {
      new function vararg(listOf(INT, FLOAT)) body { _, (signals) ->
         val chart = XYChart(400, 500)

         (signals.unwrap() as List<List<Number>>).forEachIndexed { index, signal ->
            val series = chart.addSeries("s_$index", null, signal.map { it.toDouble() })
            series.marker = SeriesMarkers.NONE
         }

         SwingWrapper(chart).displayChart()

         Value.void()
      }

      new function vararg(
         mapOfMatchers(ofType(STRING), anyType()),
         mapOfMatchers(ofType(STRING), anyType())
      ) body { _, (config, signals) ->
         val maps = (signals.value as List<Value>).map { signalSchema.parse(it) }
         val chart = XYChart(400, 500)
         val configMap = chartSchema.parse(config)

         configMap.ifPresent<String>("title") { chart.title = it }

         maps.forEach { map ->
            val series = chart.addSeries(
               map["name"].value as String,
               map.getUnwrappedOrDefault<List<Number>?>("x", null)?.map { it.toDouble() },
               (map["y"].unwrap() as List<Number>).map { it.toDouble() }
            )
            series.marker = SeriesMarkers.NONE
         }

         if (configMap.containsKey("output")) {
            val output = configMap["output"].value as String
            when((configMap["format"].value as String).toLowerCase()) {
               "png" -> BitmapEncoder.saveBitmap(chart, output, BitmapFormat.PNG)
               "jpg", "jpeg" -> BitmapEncoder.saveBitmap(chart, output, BitmapFormat.JPG)
               "bpm" -> BitmapEncoder.saveBitmap(chart, output, BitmapFormat.BMP)
               "gif" -> BitmapEncoder.saveBitmap(chart, output, BitmapFormat.GIF)
               "eps" -> VectorGraphicsEncoder.saveVectorGraphic(chart, output, VectorGraphicsFormat.EPS)
               "pdf" -> VectorGraphicsEncoder.saveVectorGraphic(chart, output, VectorGraphicsFormat.PDF)
               "svg" -> VectorGraphicsEncoder.saveVectorGraphic(chart, output, VectorGraphicsFormat.SVG)
               else -> throw CustomException("Unknown format type - available types are: png, jpg, bmp, gif, eps, pdf and svg")
            }
         } else {
            SwingWrapper(chart).displayChart()
         }

         Value.void()
      }
   }
}