package io.smnp.ext.mic

class SoundListenerLoop(private val inThreshold: Int, private val outThreshold: Int) : MicrophoneLevelLoop() {
   private var noiseReached = false
   private var silenceReached = false;

   override fun run(micRmsLevel: Int): Boolean {
      if(micRmsLevel > inThreshold) {
         noiseReached = true
      }

      if(noiseReached && micRmsLevel < outThreshold) {
         silenceReached = true
      }

      return !(noiseReached && silenceReached)
   }
}