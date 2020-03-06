package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.BlockNode
import dsl.ast.model.node.ExtendNode
import dsl.ast.model.node.Node
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class ExtendParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val simpleExtendParser = allOf(
            terminal(TokenType.EXTEND),
            assert(TypeParser(), "type to be extended"),
            assert(terminal(TokenType.AS), "'as' keyword with identifier"),
            assert(SimpleIdentifierParser(), "identifier"),
            terminal(TokenType.WITH),
            assert(mapNode(FunctionDefinitionParser()) { BlockNode(Node.NONE, listOf(it), Node.NONE) }, "method definition")
        ) {
            ExtendNode(it[1], it[3], it[5], it[0].position)
        }

        val complexExtendParser = allOf(
            terminal(TokenType.EXTEND),
            assert(TypeParser(), "type to be extended"),
            assert(terminal(TokenType.AS), "'as' keyword with identifier"),
            assert(SimpleIdentifierParser(), "identifier"),
            assert(loop(terminal(TokenType.OPEN_CURLY), assert(FunctionDefinitionParser(), "method definition or }"), terminal(TokenType.CLOSE_CURLY)) {
                begin, methods, end -> BlockNode(begin, methods, end)
            }, "block with methods' definitions or 'with' keyword with single method definition")
        ) {
            ExtendNode(it[1], it[3], it[4], it[0].position)
        }

        return oneOf(
            simpleExtendParser,
            complexExtendParser
        ).parse(input)
    }
}