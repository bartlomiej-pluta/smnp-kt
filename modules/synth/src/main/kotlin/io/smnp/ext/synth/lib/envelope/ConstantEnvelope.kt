package io.smnp.ext.synth.lib.envelope

class ConstantEnvelope : Envelope() {
   override fun eval(x: Double, length: Int) = 1.0
}