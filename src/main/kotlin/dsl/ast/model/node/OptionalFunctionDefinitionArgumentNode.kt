package dsl.ast.model.node

class OptionalFunctionDefinitionArgumentNode(identifier: Node, type: Node, defaultValue: Node) : Node(3, identifier.position) {
    val identifier: Node
    get() = children[0]

    val type: Node
    get() = children[1]

    val defaultValue: Node
    get() = children[2]

    init {
        children[0] = identifier
        children[1] = type
        children[2] = defaultValue
    }
}