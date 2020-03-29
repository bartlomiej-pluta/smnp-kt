package io.smnp.dsl.ast.model.node

class AssignmentOperatorNode(lhs: Node, operator: Node, rhs: Node) : BinaryOperatorAbstractNode(lhs, operator, rhs)