package com.bartlomiejpluta.smnp.dsl.ast.model.node

class TimeSignatureNode(numerator: Node, denominator: Node) : Node(2, numerator.position) {
   val numerator: Node
   get() = children[0]

   val denominator: Node
   get() = children[1]

   init {
      children[0] = numerator
      children[1] = denominator
   }
}