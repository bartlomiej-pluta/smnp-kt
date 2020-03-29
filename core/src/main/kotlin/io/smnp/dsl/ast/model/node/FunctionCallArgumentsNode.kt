package io.smnp.dsl.ast.model.node

import io.smnp.dsl.token.model.entity.TokenPosition

class FunctionCallArgumentsNode(items: List<Node>, position: TokenPosition) : AbstractIterableNode(items, position)