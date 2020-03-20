package io.smnp.ext.synth

class ConstantEnvelope : Envelope() {
   override fun name() = "Constant"

   override fun eval(x: Double, length: Int) = 1.0
}