package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.enumeration.ParsingResult
import dsl.ast.model.node.Node
import dsl.ast.model.node.TokenNode
import dsl.token.model.entity.Token
import dsl.token.model.entity.TokenList
import dsl.token.model.entity.TokenPosition
import dsl.token.model.enumeration.TokenType

abstract class Parser {
    fun parse(input: TokenList): ParserOutput {
        val snapshot = input.snapshot()
        val output = tryToParse(input)
        if (output.result == ParsingResult.FAILED) {
            input.restore(snapshot)
        }

        return output
    }

    protected abstract fun tryToParse(input: TokenList): ParserOutput

    companion object {

        // a -> A
        fun terminal(terminal: TokenType, createNode: (Token) -> Node = { TokenNode(it) }): Parser {
            return object : Parser() {
                override fun tryToParse(input: TokenList): ParserOutput {
                    if (input.hasCurrent() && input.current.type == terminal) {
                        val token = input.current
                        input.ahead()
                        return ParserOutput.ok(createNode(token))
                    }

                    return ParserOutput.fail()
                }
            }
        }

        // oneOf -> a | b | c | ...
        fun oneOf(vararg parsers: Parser): Parser {
            return object : Parser() {
                override fun tryToParse(input: TokenList): ParserOutput {
                    if (parsers.isEmpty()) {
                        throw RuntimeException("Provide one parser at least")
                    }

                    for (parser in parsers) {
                        val output = parser.parse(input)
                        if (output.result == ParsingResult.OK) {
                            return output
                        }
                    }

                    return ParserOutput.fail()
                }
            }
        }

        // a -> A | B | C | ...
        fun terminals(vararg terminals: TokenType, createNode: (Token) -> Node = { TokenNode(it) }): Parser {
            return oneOf(*terminals.map { terminal(it, createNode) }.toTypedArray())
        }

        // allOf -> a b c ...
        fun allOf(vararg parsers: Parser, createNode: (List<Node>) -> Node): Parser {
            return object : Parser() {
                override fun tryToParse(input: TokenList): ParserOutput {
                    if (parsers.isEmpty()) {
                        throw RuntimeException("Provide one parser at least")
                    }

                    val nodes = mutableListOf<Node>()

                    for (parser in parsers) {
                        val output = parser.parse(input)

                        if (output.result == ParsingResult.FAILED) {
                            return ParserOutput.fail()
                        }

                        nodes += output.node
                    }

                    return ParserOutput.ok(createNode(nodes))
                }

            }
        }

        // leftAssociative -> left | left OP right
        fun leftAssociativeOperator(
            lhsParser: Parser,
            allowedOperators: List<TokenType>,
            rhsParser: Parser,
            createNode: (lhs: Node, operator: Node, rhs: Node) -> Node
        ): Parser {
            return object : Parser() {
                override fun tryToParse(input: TokenList): ParserOutput {
                    val opParser = terminals(*allowedOperators.toTypedArray())
                    var lhs = lhsParser.parse(input)
                    if (lhs.result == ParsingResult.OK) {
                        var op = opParser.parse(input)
                        while (op.result == ParsingResult.OK) {
                            val rhs = rhsParser.parse(input)
                            if (rhs.result == ParsingResult.FAILED) { // Todo: Not sure if it should be here
                                return ParserOutput.fail()
                            }
                            lhs = ParserOutput.ok(createNode(lhs.node, op.node, rhs.node))
                            op = opParser.parse(input)
                        }
                        return lhs
                    }

                    return ParserOutput.fail()
                }

            }
        }

        // repeat -> item*
        fun repeat(itemParser: Parser, createNode: (List<Node>, TokenPosition) -> Node): Parser {
            return object : Parser() {
                override fun tryToParse(input: TokenList): ParserOutput {
                    val items = mutableListOf<Node>()
                    while (true) {
                        val output = itemParser.parse(input)
                        if (output.result == ParsingResult.OK) {
                            items += output.node
                        } else if (items.isEmpty()) {
                            return ParserOutput.fail()
                        } else {
                            return ParserOutput.ok(createNode(items, items.first().position))
                        }
                    }
                }

            }
        }

        // loop -> begin item* end
        fun loop(
            beginParser: Parser,
            itemParser: Parser,
            endParser: Parser,
            createNode: (Node, List<Node>, Node) -> Node
        ): Parser {
            return object : Parser() {
                override fun tryToParse(input: TokenList): ParserOutput {
                    val items = mutableListOf<Node>()
                    val begin = beginParser.parse(input)
                    if (begin.result == ParsingResult.OK) {
                        while (true) {
                            val end = endParser.parse(input)
                            if (end.result == ParsingResult.OK) {
                                return ParserOutput.ok(createNode(begin.node, items, end.node))
                            }
                            val item = itemParser.parse(input)
                            if (item.result == ParsingResult.FAILED) {
                                return ParserOutput.fail()
                            }
                            items += item.node
                        }
                    }

                    return ParserOutput.fail()
                }

            }
        }

        fun assert(parser: Parser, expected: String): Parser {
            return object : Parser() {
                override fun tryToParse(input: TokenList): ParserOutput {
                    val output = parser.parse(input)

                    if (output.result == ParsingResult.FAILED) {
                        throw RuntimeException("Expected $expected")
                    }

                    return output
                }
            }
        }

        // optional -> a?
        fun optional(parser: Parser): Parser {
            return object : Parser() {
                override fun tryToParse(input: TokenList): ParserOutput {
                    val output = parser.parse(input)
                    return if (output.result == ParsingResult.OK) output else ParserOutput.ok(Node.NONE)
                }
            }
        }

        fun mapNode(parser: Parser, mapper: (Node) -> Node): Parser {
            return object : Parser() {
                override fun tryToParse(input: TokenList): ParserOutput {
                    return parser.parse(input).map(mapper)
                }

            }
        }
    }
}