package com.bartlomiejpluta.smnp.dsl.ast.model.node

import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenPosition

class RootNode(nodes: List<Node>, position: TokenPosition) : Node(nodes, position)