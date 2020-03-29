package io.smnp.dsl.ast.model.node

import io.smnp.dsl.token.model.entity.Token

abstract class AtomAbstractNode(val token: Token) : Node(1, token.position) {
    override fun pretty(prefix: String, last: Boolean, first: Boolean) {
        println(prefix + (if (first) "" else if (last) "└─" else "├─") + this::class.simpleName + " " + position)
        println(prefix + (if (last) "   " else "│  ") + "└ " + token)
    }
}

