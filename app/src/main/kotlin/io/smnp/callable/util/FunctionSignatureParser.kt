package io.smnp.callable.util

import io.smnp.callable.signature.Signature
import io.smnp.dsl.ast.model.node.*
import io.smnp.error.InvalidSignatureException
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.type.enumeration.DataType
import io.smnp.type.matcher.Matcher
import io.smnp.type.matcher.Matcher.Companion.allTypes
import io.smnp.type.matcher.Matcher.Companion.listOfMatchers
import io.smnp.type.matcher.Matcher.Companion.mapOfMatchers
import io.smnp.type.matcher.Matcher.Companion.ofType
import io.smnp.type.matcher.Matcher.Companion.oneOf
import io.smnp.type.matcher.Matcher.Companion.optional

object FunctionSignatureParser {
    private data class SignatureMetadata(val hasRegular: Boolean, val hasOptional: Boolean, val hasVararg: Boolean)

    fun parseSignature(signature: FunctionDefinitionArgumentsNode): Signature {
        val (_, _, hasVararg) = assertArgumentsPositions(signature)
        assertArgumentsNames(signature)

        val matchers = signature.items.map {
            when (it) {
                is RegularFunctionDefinitionArgumentNode -> matcherForUnionTypeNode(it.type as UnionTypeNode)
                is OptionalFunctionDefinitionArgumentNode -> optional(matcherForUnionTypeNode(it.type as UnionTypeNode))
                else -> throw ShouldNeverReachThisLineException()
            }
        }

        return if (hasVararg) Signature.vararg(
            matchers.last(),
            *matchers.dropLast(1).toTypedArray()
        ) else Signature.simple(*matchers.toTypedArray())
    }

    private fun assertArgumentsNames(signature: FunctionDefinitionArgumentsNode) {
        signature.items.groupingBy {
            when (it) {
                is RegularFunctionDefinitionArgumentNode -> (it.identifier as IdentifierNode).token.rawValue
                is OptionalFunctionDefinitionArgumentNode -> (it.identifier as IdentifierNode).token.rawValue
                else -> throw ShouldNeverReachThisLineException()
            }
        }.eachCount().forEach {
            if (it.value > 1) {
                throw InvalidSignatureException("Duplicated argument name of '${it.key}'")
            }
        }
    }


    private fun assertArgumentsPositions(signature: FunctionDefinitionArgumentsNode): SignatureMetadata {
        val firstOptional = signature.items.indexOfFirst { it is OptionalFunctionDefinitionArgumentNode }
        val lastRegular = signature.items.indexOfLast { it is RegularFunctionDefinitionArgumentNode }
        val vararg =
            signature.items.indexOfFirst { it is RegularFunctionDefinitionArgumentNode && it.vararg != Node.NONE }

        val metadata = SignatureMetadata(
            lastRegular != -1,
            firstOptional != -1,
            vararg != -1
        )

        if (metadata.hasVararg && metadata.hasOptional) {
            throw InvalidSignatureException("Optional arguments and vararg cannot be mixed in same signature")
        }

        if (metadata.hasRegular && metadata.hasOptional && firstOptional < lastRegular) {
            throw InvalidSignatureException("Optional arguments should be at the very end of arguments list")
        }

        if (metadata.hasVararg && vararg != signature.items.size - 1) {
            throw InvalidSignatureException("Vararg arguments should be at the very end of arguments list")
        }

        return metadata
    }

    private fun matcherForUnionTypeNode(unionTypeNode: UnionTypeNode): Matcher {
        if (unionTypeNode.items.isEmpty()) {
            return allTypes()
        }

        if (unionTypeNode.items.size == 1) {
            return matcherForSingleTypeNode(
                unionTypeNode.items[0] as SingleTypeNode
            )
        }

        return oneOf(*unionTypeNode.items.map {
            matcherForSingleTypeNode(
                it as SingleTypeNode
            )
        }.toTypedArray())
    }

    fun matcherForSingleTypeNode(singleTypeNode: SingleTypeNode): Matcher {
        // TODO
        val type = DataType.valueOf((singleTypeNode.type as IdentifierNode).token.rawValue.toUpperCase())
        if (singleTypeNode.specifiers == Node.NONE) {
            return ofType(type)
        } else if (type == DataType.LIST && singleTypeNode.specifiers.children.size == 1) {
            return listSpecifier(singleTypeNode.specifiers.children[0] as TypeSpecifierNode)
        } else if (type == DataType.MAP && singleTypeNode.specifiers.children.size == 2) {
            return mapSpecifier(
                singleTypeNode.specifiers.children[0] as TypeSpecifierNode,
                singleTypeNode.specifiers.children[1] as TypeSpecifierNode
            )
        }

        throw ShouldNeverReachThisLineException()
    }

    private fun listSpecifier(listSpecifierNode: TypeSpecifierNode): Matcher {
        val types = mutableListOf<Matcher>()

        if (listSpecifierNode.items.isEmpty()) {
            types.add(allTypes())
        }

        listSpecifierNode.items.forEach { types.add(
            matcherForSingleTypeNode(
                it as SingleTypeNode
            )
        ) }

        return listOfMatchers(*types.toTypedArray())
    }

    private fun mapSpecifier(keySpecifierNode: TypeSpecifierNode, valueSpecifierNode: TypeSpecifierNode): Matcher {
        val keys = mutableListOf<Matcher>()
        val values = mutableListOf<Matcher>()

        if (keySpecifierNode.items.isEmpty()) {
            keys.add(allTypes())
        }

        if (valueSpecifierNode.items.isEmpty()) {
            values.add(allTypes())
        }

        keySpecifierNode.items.forEach { keys.add(
            matcherForSingleTypeNode(
                it as SingleTypeNode
            )
        ) }
        valueSpecifierNode.items.forEach { values.add(
            matcherForSingleTypeNode(
                it as SingleTypeNode
            )
        ) }

        return mapOfMatchers(keys, values)
    }
}