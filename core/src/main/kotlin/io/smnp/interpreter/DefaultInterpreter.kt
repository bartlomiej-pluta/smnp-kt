package io.smnp.interpreter

import io.smnp.dsl.ast.parser.RootParser
import io.smnp.dsl.token.tokenizer.DefaultTokenizer
import io.smnp.environment.DefaultEnvironment
import io.smnp.environment.Environment
import io.smnp.evaluation.evaluator.RootEvaluator
import java.io.File

class DefaultInterpreter  {
   private val tokenizer = DefaultTokenizer()
   private val parser = RootParser()
   private val evaluator = RootEvaluator()

   fun run(
      code: String,
      environment: Environment = DefaultEnvironment(),
      printTokens: Boolean = false,
      printAst: Boolean = false,
      dryRun: Boolean = false
   ): Environment {
      val lines = code.split("\n")
      return run(lines, "<inline>", environment, printTokens, printAst, dryRun)
   }

   private fun run(
      lines: List<String>,
      source: String,
      environment: Environment,
      printTokens: Boolean,
      printAst: Boolean,
      dryRun: Boolean
   ): Environment {
      environment.loadModule("smnp.lang")

      val tokens = tokenizer.tokenize(lines, source)

      val ast = parser.parse(tokens)

      if (!dryRun) {
         evaluator.evaluate(ast.node, environment)
      }

      if (printTokens) println(tokens)
      if (printAst) ast.node.pretty()

      return environment
   }

   fun run(
      file: File,
      environment: Environment = DefaultEnvironment(),
      printTokens: Boolean = false,
      printAst: Boolean = false,
      dryRun: Boolean = false
   ): Environment {
      val lines = file.readLines()
      return run(lines, file.canonicalPath, environment, printTokens, printAst, dryRun)
   }
}