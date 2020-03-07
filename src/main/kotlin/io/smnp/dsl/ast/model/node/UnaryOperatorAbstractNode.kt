package io.smnp.dsl.ast.model.node

abstract class UnaryOperatorAbstractNode(operator: Node, operand: Node) : Node(2, operator.position) {
    val operator: Node
    get() = children[0]

    val operand: Node
    get() = children[1]

    init {
        children[0] = operator
        children[1] = operand
    }
}