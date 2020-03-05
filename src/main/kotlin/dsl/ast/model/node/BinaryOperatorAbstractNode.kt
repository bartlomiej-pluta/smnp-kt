package dsl.ast.model.node

abstract class BinaryOperatorAbstractNode(lhs: Node, operator: Node, rhs: Node) : Node(3, operator.position) {
    val lhs: Any
    get() = children[0]

    val operator: Any
    get() = children[1]

    val rhs: Any
    get() = children[2]

    init {
        children[0] = lhs
        children[1] = operator
        children[2] = rhs
    }
}