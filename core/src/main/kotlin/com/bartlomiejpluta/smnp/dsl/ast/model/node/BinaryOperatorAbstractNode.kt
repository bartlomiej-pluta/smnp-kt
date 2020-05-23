package com.bartlomiejpluta.smnp.dsl.ast.model.node

abstract class BinaryOperatorAbstractNode(lhs: Node, operator: Node, rhs: Node) : Node(3, operator.position) {
    operator fun component1() = children[0]
    operator fun component2() = children[1]
    operator fun component3() = children[2]

    val lhs: Node
    get() = children[0]

    val operator: Node
    get() = children[1]

    val rhs: Node
    get() = children[2]

    init {
        children[0] = lhs
        children[1] = operator
        children[2] = rhs
    }
}