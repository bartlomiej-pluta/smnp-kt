package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class AtomParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val parenthesesParser = allOf(
            terminal(TokenType.OPEN_PAREN),
            ExpressionParser(),
            terminal(TokenType.CLOSE_PAREN)
        ) { (_, expression) -> expression }

        val literalParser = oneOf(
            parenthesesParser,
            ComplexIdentifierParser(),
            StaffParser(),
            ListParser(),
            LiteralParser(),
            MapParser()
        )

        return literalParser.parse(input)
    }
}