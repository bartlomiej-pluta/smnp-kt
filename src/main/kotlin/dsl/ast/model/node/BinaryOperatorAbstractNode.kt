package dsl.ast.model.node

abstract class BinaryOperatorAbstractNode(lhs: Node, operator: Node, rhs: Node) : Node(3, operator.position) {
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