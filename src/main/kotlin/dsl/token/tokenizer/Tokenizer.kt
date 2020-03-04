package dsl.token.tokenizer

import dsl.token.model.entity.Token
import dsl.token.model.entity.TokenPosition
import dsl.token.model.entity.TokenizerOutput
import dsl.token.model.enumeration.TokenType

interface Tokenizer {
    fun tokenize(input: String, current: Int, line: Int): TokenizerOutput

    companion object {
        // Char regex
        fun regex(type: TokenType, pattern: String): Tokenizer {
            return object : Tokenizer {
                override fun tokenize(input: String, current: Int, line: Int): TokenizerOutput {
                    var consumedChars = 0
                    var value = ""

                    while (current + consumedChars < input.length && pattern.toRegex().matches(input[current + consumedChars].toString())) {
                        value += input[current + consumedChars]
                        consumedChars += 1
                    }

                    return TokenizerOutput.produce(consumedChars, value, type, line, current)
                }
            }
        }

        // Literal keyword ("function", "or", ".")
        fun keyword(type: TokenType, keyword: String): Tokenizer {
            return object : Tokenizer {
                override fun tokenize(input: String, current: Int, line: Int): TokenizerOutput {
                    if ((input.length >= current + keyword.length) && (input.substring(
                            current,
                            current + keyword.length
                        ) == keyword)
                    ) {
                        return TokenizerOutput.produce(keyword.length, keyword, type, line, current)
                    }

                    return TokenizerOutput.NONE
                }
            }
        }

        // One of keywords
        fun keywords(type: TokenType, vararg keywords: String): Tokenizer {
            return object : Tokenizer {
                override fun tokenize(input: String, current: Int, line: Int): TokenizerOutput {
                    for (keyword in keywords) {
                        val output = keyword(type, keyword).tokenize(input, current, line)
                        if (output.consumed()) {
                            return output
                        }
                    }

                    return TokenizerOutput.NONE
                }

            }
        }

        // Token for regular TokenType
        fun default(type: TokenType): Tokenizer {
            return object : Tokenizer {
                override fun tokenize(input: String, current: Int, line: Int): TokenizerOutput {
                    return keyword(type, type.token).tokenize(input, current, line)
                }

            }
        }

        // Isolate dsl.token (for example "function" | "functions" | "function s")
        fun separated(tokenizer: Tokenizer, end: String = "\\W"): Tokenizer {
            return object : Tokenizer {
                override fun tokenize(input: String, current: Int, line: Int): TokenizerOutput {
                    val output = tokenizer.tokenize(input, current, line)
                    if (output.consumed()) {
                        if ((input.length > current + output.consumedChars) && end.toRegex().matches(input[current + output.consumedChars].toString())) {
                            return output
                        }

                        if ((input.length == current + output.consumedChars)) {
                            return output
                        }
                    }

                    return TokenizerOutput.NONE
                }

            }
        }

        // Change dsl.token value (rawValue will be kept)
        fun mapValue(tokenizer: Tokenizer, mapper: (Any) -> Any): Tokenizer {
            return object : Tokenizer {
                override fun tokenize(input: String, current: Int, line: Int): TokenizerOutput {
                    val output = tokenizer.tokenize(input, current, line)
                    if(output.consumed()) {
                        return output.mapToken { output.token.mapValue(mapper) }
                    }

                    return TokenizerOutput.NONE
                }
            }
        }

        // Complex tokenizer consisting of smaller ones (like "3.14" = regex(\d) + keyword(.) + regex(\d))
        fun combined(createToken: (List<String>, TokenPosition) -> Token, vararg tokenizers: Tokenizer): Tokenizer {
            return object : Tokenizer {
                override fun tokenize(input: String, current: Int, line: Int): TokenizerOutput {
                    var consumedChars = 0
                    val tokens: MutableList<Token> = mutableListOf()

                    for(tokenizer in tokenizers) {
                        val output = tokenizer.tokenize(input, current + consumedChars, line)
                        if(output.consumed()) {
                            consumedChars += output.consumedChars
                            tokens.add(output.token)
                        } else {
                            return TokenizerOutput.NONE
                        }
                    }

                    if(consumedChars == 0) {
                        return TokenizerOutput.NONE
                    }

                    return TokenizerOutput.produce(consumedChars, createToken(tokens.map { it.rawValue }, TokenPosition(line, tokens.first().position.beginCol, tokens.last().position.endCol)))
                }

            }
        }

        // First matched tokenizer
        fun firstOf(tokenizers: List<Tokenizer>): Tokenizer {
            return object : Tokenizer {
                override fun tokenize(input: String, current: Int, line: Int): TokenizerOutput {
                    for (tokenizer in tokenizers) {
                        val output = tokenizer.tokenize(input, current, line)
                        if(output.consumed()) {
                            return output
                        }
                    }

                    return TokenizerOutput.NONE
                }

            }
        }
    }
}