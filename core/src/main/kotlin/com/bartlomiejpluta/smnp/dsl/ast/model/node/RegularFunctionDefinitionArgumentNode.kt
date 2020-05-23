package com.bartlomiejpluta.smnp.dsl.ast.model.node

class RegularFunctionDefinitionArgumentNode(identifier: Node, type: Node, vararg: Node) : Node(3, identifier.position) {
    val identifier
    get() = children[0]

    val type
    get() = children[1]

    val vararg
    get() = children[2]

    init {
        children[0] = identifier
        children[1] = type
        children[2] = vararg
    }
}