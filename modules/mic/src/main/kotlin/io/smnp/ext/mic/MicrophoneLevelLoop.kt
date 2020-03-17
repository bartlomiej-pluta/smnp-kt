package io.smnp.ext.mic

import io.smnp.error.CustomException
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.TargetDataLine
import kotlin.math.pow

abstract class MicrophoneLevelLoop {
   protected abstract fun run(micRmsLevel: Int): Boolean

   fun run() {
      val format = AudioFormat(44100F, 8, 1, true, false)
      val info = DataLine.Info(TargetDataLine::class.java, format)

      if (!AudioSystem.isLineSupported(info)) {
         throw CustomException("The audio system target line is not supported")
      }

      val line = AudioSystem.getLine(info) as TargetDataLine
      line.open(format)
      line.start()

      val bytes = ByteArray(line.bufferSize / 5)
      while (true) {
         line.read(bytes, 0, bytes.size)
         if(!run(calculateRms(bytes))) {
            break
         }
         Thread.sleep(10)
      }

      line.stop()
      line.close()
   }

   private fun calculateRms(audioData: ByteArray): Int {
      val sum = audioData.map { it.toLong() }.sum()
      val avg = sum / audioData.size.toDouble()

      val sumMeanSquare = audioData.map { it.toDouble() }.map { (it - avg).pow(2.0) }.reduce { acc, n -> acc + n }
      val averageMeanSquare = sumMeanSquare / audioData.size

      return (averageMeanSquare.pow(0.5)).toInt()
   }
}