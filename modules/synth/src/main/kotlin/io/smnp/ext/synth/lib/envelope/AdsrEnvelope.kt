package io.smnp.ext.synth.lib.envelope

class AdsrEnvelope(private val p1: Double, private val p2: Double, private val p3: Double, private val s: Double) : Envelope() {

   override fun eval(x: Double, length: Int): Double {
      val p1x = length * p1
      val p2x = length * p2
      val p3x = length * p3
      val f1 = quad(0.0 to 0.0, p1x to 1.0, 2.0 * p1x to 0.0)
      val f2 = quad(p1x to 1.0, p2x to s, p1x + 2.0 * (p2x - p1x) to 1.0)
      val f3 = quad(p3x to s, length.toDouble() to 0.0, p3x + 2.0 * (length - p3x) to s)

      return if (x >= 0 && x < p1x) {
         f1(x)
      } else if (x >= p1x && x < p2x) {
         f2(x)
      } else if (x >= p2x && x < p3x) {
         s
      } else {
         f3(x)
      }
   }
}