package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

class RootNode(nodes: List<Node>, position: TokenPosition) : Node(nodes, position)