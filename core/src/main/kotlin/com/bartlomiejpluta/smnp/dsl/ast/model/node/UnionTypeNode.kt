package com.bartlomiejpluta.smnp.dsl.ast.model.node

import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenPosition

class UnionTypeNode(items: List<Node>, position: TokenPosition) : AbstractIterableNode(items, position)