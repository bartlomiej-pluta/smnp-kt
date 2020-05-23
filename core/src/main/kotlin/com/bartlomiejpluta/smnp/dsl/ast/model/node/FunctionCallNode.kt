package com.bartlomiejpluta.smnp.dsl.ast.model.node

class FunctionCallNode(identifier: Node, arguments: Node) : Node(2, identifier.position) {
    operator fun component1() = children[0]
    operator fun component2() = children[1]

    val identifier: Node
    get() = children[0]

    val arguments: Node
    get() = children[1]

    init {
        children[0] = identifier
        children[1] = arguments
    }
}