package com.bartlomiejpluta.smnp.dsl.ast.model.node

class LogicOperatorNode(lhs: Node, operator: Node, rhs: Node) : BinaryOperatorAbstractNode(lhs, operator, rhs)