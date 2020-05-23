package com.bartlomiejpluta.smnp.dsl.ast.model.node

import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenPosition

class TypeSpecifiersNode(specifiers: List<Node>, position: TokenPosition) : Node(specifiers, position)