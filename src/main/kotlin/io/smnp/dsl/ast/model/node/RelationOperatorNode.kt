package io.smnp.dsl.ast.model.node

class RelationOperatorNode(lhs: Node, operator: Node, rhs: Node) : BinaryOperatorAbstractNode(lhs, operator, rhs)