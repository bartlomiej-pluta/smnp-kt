package dsl.ast.model.node

import dsl.token.model.entity.Token

class TokenNode(_token: Token) : Node(1, _token.position) {
    var type: Any
    get() = children[0]
    set(value) {
        children[0] = value
    }

    init {
        type = _token
    }
}