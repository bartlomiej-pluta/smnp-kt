package dsl.ast.model.node

class FunctionCallNode(identifier: Node, arguments: Node) : Node(2, identifier.position) {
    val identifier: Node
    get() = children[0]

    val arguments: Node
    get() = children[1]

    init {
        children[0] = identifier
        children[1] = arguments
    }
}