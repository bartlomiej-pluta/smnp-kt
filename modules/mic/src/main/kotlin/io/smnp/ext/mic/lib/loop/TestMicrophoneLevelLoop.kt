package io.smnp.ext.mic.lib.loop

import kotlin.math.min

class TestMicrophoneLevelLoop : MicrophoneLevelLoop() {
   private val MAX_WIDTH = 70

   override fun run(micRmsLevel: Int): Boolean {
      System.out.printf("\rMicrophone level: [${micRmsLevel.toString().padStart(4)}] [")
      val bar = min(micRmsLevel, MAX_WIDTH)

      repeat(bar) { print("#") }
      repeat(MAX_WIDTH - bar) { print(".") }
      print("]${if(micRmsLevel > MAX_WIDTH) "*" else " "}")


      return true
   }
}