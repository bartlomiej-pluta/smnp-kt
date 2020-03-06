package dsl.ast.model.node

class ReturnNode(value: Node) : Node(1, value.position) {
    val value: Node
    get() = children[0]

    init {
        children[0] = value
    }
}