package dsl.ast.model.node

class ThrowNode(value: Node) : Node(1, value.position) {
    val value: Any
        get() = children[0]

    init {
        children[0] = value
    }
}