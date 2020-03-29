package io.smnp.dsl.ast.model.node

import io.smnp.dsl.token.model.entity.TokenPosition

class RootNode(nodes: List<Node>, position: TokenPosition) : Node(nodes, position)