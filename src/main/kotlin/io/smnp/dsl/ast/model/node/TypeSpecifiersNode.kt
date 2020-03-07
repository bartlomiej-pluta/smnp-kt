package io.smnp.dsl.ast.model.node

import io.smnp.dsl.token.model.entity.TokenPosition

class TypeSpecifiersNode(specifiers: List<Node>, position: TokenPosition) : Node(specifiers, position)