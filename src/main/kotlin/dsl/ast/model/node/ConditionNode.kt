package dsl.ast.model.node

class ConditionNode(trueBranchToken: Node, condition: Node, trueBranch: Node, falseBranchToken: Node, falseBranch: Node) : Node(3, trueBranchToken.position) {
    val condition: Any
    get() = children[0]

    val trueBranch: Any
    get() = children[1]

    val falseBranch: Any
    get() = children[2]

    init {
        children[0] = condition
        children[1] = trueBranch
        children[2] = falseBranch
    }
}