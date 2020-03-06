package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

class TypeSpecifiersNode(specifiers: List<Node>, position: TokenPosition) : Node(specifiers, position)