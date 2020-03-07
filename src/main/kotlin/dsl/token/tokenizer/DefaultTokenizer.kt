package dsl.token.tokenizer

import dsl.token.model.entity.Token
import dsl.token.model.entity.TokenList
import dsl.token.model.entity.TokenPosition
import dsl.token.model.entity.TokenizerOutput
import dsl.token.model.enumeration.TokenType
import dsl.token.tokenizer.Tokenizer.Companion.default
import dsl.token.tokenizer.Tokenizer.Companion.keywords
import dsl.token.tokenizer.Tokenizer.Companion.mapValue
import dsl.token.tokenizer.Tokenizer.Companion.regex
import dsl.token.tokenizer.Tokenizer.Companion.separated
import error.InvalidSyntaxException

class DefaultTokenizer : Tokenizer {
    private val tokenizers = listOf(
        default(TokenType.ARROW),

        // Double-character operators
        keywords(TokenType.RELATION, "==", "!=", "<=", ">="),
        default(TokenType.DOUBLE_ASTERISK),

        // Characters
        default(TokenType.OPEN_CURLY),
        default(TokenType.CLOSE_CURLY),
        default(TokenType.OPEN_PAREN),
        default(TokenType.CLOSE_PAREN),
        default(TokenType.OPEN_SQUARE),
        default(TokenType.CLOSE_SQUARE),
        default(TokenType.OPEN_ANGLE),
        default(TokenType.CLOSE_ANGLE),
        default(TokenType.SEMICOLON),
        default(TokenType.ASTERISK),
        default(TokenType.PERCENT),
        default(TokenType.ASSIGN),
        default(TokenType.COLON),
        default(TokenType.COMMA),
        default(TokenType.SLASH),
        default(TokenType.MINUS),
        default(TokenType.PLUS),
        default(TokenType.CARET),
        default(TokenType.DOTS),
        default(TokenType.AMP),
        default(TokenType.DOT),

        // Types
        mapValue(separated(FloatTokenizer())) { (it as String).toFloat() },
        mapValue(separated(regex(TokenType.INTEGER, "\\d"))) { (it as String).toInt() },
        StringTokenizer(),
        separated(NoteTokenizer()),
        mapValue(separated(keywords(TokenType.BOOL, "true", "false"))) { it == "true" },

        // Keywords
        separated(default(TokenType.FUNCTION)),
        separated(default(TokenType.RETURN)),
        separated(default(TokenType.EXTEND)),
        separated(default(TokenType.IMPORT)),
        separated(default(TokenType.THROW)),
        separated(default(TokenType.FROM)),
        separated(default(TokenType.WITH)),
        separated(default(TokenType.ELSE)),
        separated(default(TokenType.AND)),
        separated(default(TokenType.NOT)),
        separated(default(TokenType.AS)),
        separated(default(TokenType.IF)),
        separated(default(TokenType.OR)),

        // Identifier (couldn't be before keywords!)
        regex(TokenType.IDENTIFIER, "\\w"),

        // Whitespaces
        regex(TokenType.NONE, "\\s"),

        // Comments
        CommentTokenizer()
    )

    private val filters: List<(Token) -> Boolean> = listOf(
        { token -> token.type != TokenType.NONE },
        { token -> token.type != TokenType.COMMENT }
    )

    private val tokenizer = Tokenizer.firstOf(tokenizers)

    fun tokenize(lines: List<String>): TokenList {
        val tokens: MutableList<Token> = mutableListOf()

        for ((index, line) in lines.withIndex()) {
            var current = 0
            while (current < line.length) {
                val output = tokenize(line, current, index)

                if (!output.consumed()) {
                    throw InvalidSyntaxException("Unknown symbol ${line[current]}", TokenPosition(index, current, -1))
                }

                current += output.consumedChars
                tokens.add(output.token)
            }
        }

        val filteredTokens = filterTokens(tokens.toList())
        return TokenList(filteredTokens, lines)
    }

    private fun filterTokens(tokens: List<Token>): List<Token> {
        return tokens.filter { token -> filters.all { filter -> filter(token) } }
    }

    override fun tokenize(input: String, current: Int, line: Int): TokenizerOutput {
        return tokenizer.tokenize(input, current, line)
    }
}