package io.smnp.math

class Fraction(val numerator: Int, val denominator: Int) : Comparable<Fraction> {
   init {
      if (denominator == 0) {
         throw RuntimeException("Invalid fraction: denominator can not be of 0")
      }
   }

   operator fun component1() = numerator
   operator fun component2() = denominator

   val decimal by lazy { numerator.toDouble() / denominator }
   val inversed by lazy { Fraction(denominator, numerator) }

   val simplified by lazy {
      val gcd = greatestCommonDenominator(numerator, denominator)
      Fraction(numerator / gcd, denominator / gcd)
   }

   private fun greatestCommonDenominator(a: Int, b: Int): Int = if (b == 0) a else greatestCommonDenominator(b, a % b)

   operator fun unaryMinus() = Fraction(-this.numerator, this.denominator)

   operator fun plus(second: Fraction) =
      if (this.denominator == second.denominator) Fraction(this.numerator + second.numerator, denominator)
      else {
         val numerator = numerator * second.denominator + second.numerator * denominator
         val denominator = denominator * second.denominator
         Fraction(numerator, denominator)
      }

   operator fun minus(second: Fraction) = (this + (-second))

   operator fun times(num: Int) = Fraction(numerator * num, denominator)

   operator fun times(second: Fraction) =
      Fraction(numerator * second.numerator, denominator * second.denominator)

   operator fun div(num: Int) = this.inversed * num

   operator fun div(second: Fraction) = this * second.inversed

   override fun compareTo(other: Fraction) = decimal.compareTo(other.decimal)

   override fun toString() =
      this.simplified.let { if (it.denominator == 1) it.numerator.toString() else "${it.numerator}/${it.denominator}" }

   override fun equals(other: Any?): Boolean {
      if (other !is Fraction) {
         return false
      }

      val (aNum, aDen) = this.simplified
      val (bNum, bDen) = other.simplified

      return aNum == bNum && aDen == bDen
   }

   companion object {
      fun of(int: Int): Fraction {
         return Fraction(int, 1)
      }
   }
}