package io.smnp.dsl.ast.model.node

class LoopNode(iterator: Node, parameters: Node, operator: Node, statement: Node, filter: Node): Node(4, operator.position) {
    operator fun component1() = children[0]
    operator fun component2() = children[1]
    operator fun component3() = children[2]
    operator fun component4() = children[3]

    val iterator: Node
    get() = children[0]

    val parameters: Node
    get() = children[1]

    val statement: Node
    get() = children[2]

    val filter: Node
    get() = children[3]

    init {
        children[0] = iterator
        children[1] = parameters
        children[2] = statement
        children[3] = filter
    }
}