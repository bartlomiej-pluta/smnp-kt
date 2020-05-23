package com.bartlomiejpluta.smnp.ext.dsp.lib.complex

import kotlin.math.atan2
import kotlin.math.pow

data class Complex(val re: Double, val im: Double) {

   constructor(a: Double) : this(kotlin.math.cos(a), kotlin.math.sin(a))

   val mod: Double
   get() = squaresSum.pow(0.5)

   private val squaresSum: Double
   get() = re.pow(2.0) + im.pow(2.0)

   val arg: Double
      get() = atan2(im, re)

   override fun toString() = "$re${if(im > 0) "+$im" else "$im"}"

   operator fun plus(z: Complex) = Complex(re + z.re, im + z.im)

   operator fun minus(z: Complex) = Complex(re - z.re, im - z.im)

   operator fun times(z: Complex) = Complex(re * z.re - im * z.im, re * z.im + im * z.re)
}