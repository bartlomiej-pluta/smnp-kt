package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

abstract class BinaryOperatorAbstractNode(position: TokenPosition) : Node(3, position) {
    var lhs: Any
    get() = children[0]
    set(value) {
        children[0] = value
    }

    var operator: Any
    get() = children[1]
    set(value) {
        children[1] = value
    }

    var rhs: Any
    get() = children[2]
    set(value) {
        children[2] = value
    }
}