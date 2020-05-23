package com.bartlomiejpluta.smnp.dsl.ast.model.node

class SumOperatorNode(lhs: Node, operator: Node, rhs: Node) : BinaryOperatorAbstractNode(lhs, operator, rhs)