package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.Node
import dsl.token.model.entity.TokenList
import dsl.token.model.entity.TokenPosition
import dsl.token.model.enumeration.TokenType

class AbstractIterableNode(beginNode: Node, items: List<Node>) : Node(items, beginNode.position)

abstract class AbstractIterableParser(
    private val beginTokenType: TokenType,
    private val itemParser: Parser,
    private val endTokenType: TokenType,
    private val createNode: (List<Node>, TokenPosition) -> Node
) : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val emptyIterableParser = allOf(listOf(terminal(beginTokenType), terminal(endTokenType))) { object : Node(0, it[0].position) {} }

        val nonEmptyIterableParser = allOf(
            listOf(terminal(beginTokenType),
                allOf(listOf(
                    itemParser,
                    optional(repeat(allOf(
                        listOf(
                            terminal(TokenType.COMMA),
                            itemParser
                        )
                    ) { it[1] }) { list, position -> object : Node(list, position) {} }
                ))) { list ->
                    object : Node(listOf(list[0]) + list[1].children, TokenPosition.NONE) {} },
                terminal(endTokenType)
            )
        ) { createNode(it[1].children.toList() as List<Node>, it[0].position) }

        return oneOf(
            listOf(
                emptyIterableParser,
                nonEmptyIterableParser
            )
        ).parse(input)
    }

}