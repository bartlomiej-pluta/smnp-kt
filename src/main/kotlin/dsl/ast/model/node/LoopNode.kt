package dsl.ast.model.node

class LoopNode(iterator: Node, parameters: Node, operator: Node, statement: Node, filter: Node): Node(4, operator.position) {
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