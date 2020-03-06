package dsl.ast.model.node

class TypeNode(type: Node, specifiers: Node) : Node(2, type.position) {
    val type: Node
    get() = children[0]

    val specifiers: Node
    get() = children[1]

    init {
        children[0] = type
        children[1] = specifiers
    }
}