package com.bartlomiejpluta.smnp.dsl.ast.model.node

import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenPosition

class FunctionDefinitionNode(identifier: Node, arguments: Node, body: Node, position: TokenPosition) : Node(3, position) {
    operator fun component1() = children[0]
    operator fun component2() = children[1]
    operator fun component3() = children[2]

    val identifier: Node
    get() = children[0]

    val arguments: Node
    get() = children[1]

    val body: Node
    get() = children[2]

    init {
        children[0] = identifier
        children[1] = arguments
        children[2] = body
    }
}