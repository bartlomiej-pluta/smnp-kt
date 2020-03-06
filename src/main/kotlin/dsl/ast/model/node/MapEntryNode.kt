package dsl.ast.model.node

class MapEntryNode(key: Node, operator: Node, value: Node) : Node(2, operator.position) {
    val key: Node
    get() = children[0]

    val value: Node
    get() = children[1]

    init {
        children[0] = key
        children[1] = value
    }
}