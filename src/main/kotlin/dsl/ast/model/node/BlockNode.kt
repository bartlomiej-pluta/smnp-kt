package dsl.ast.model.node

class BlockNode(begin: Node, statements: List<Node>, end: Node) : Node(statements, begin.position) {
    val statements: List<Any>
    get() = children

    init {
        children = statements.toMutableList()
    }
}