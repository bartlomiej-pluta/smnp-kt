package io.smnp.dsl.ast.model.node

class ConditionNode(trueBranchToken: Node, condition: Node, trueBranch: Node, falseBranchToken: Node, falseBranch: Node) : Node(3, trueBranchToken.position) {
    operator fun component1(): Node = children[0]
    operator fun component2(): Node = children[1]
    operator fun component3(): Node = children[2]

    val condition: Node
    get() = children[0]

    val trueBranch: Node
    get() = children[1]

    val falseBranch: Node
    get() = children[2]

    init {
        children[0] = condition
        children[1] = trueBranch
        children[2] = falseBranch
    }
}