package io.smnp.interpreter

import io.smnp.dsl.ast.parser.RootParser
import io.smnp.dsl.token.tokenizer.DefaultTokenizer
import io.smnp.environment.DefaultEnvironment
import io.smnp.environment.Environment
import io.smnp.error.SmnpException
import io.smnp.evaluation.evaluator.RootEvaluator
import io.smnp.evaluation.model.enumeration.EvaluationResult
import java.io.File
import java.lang.System.err
import kotlin.system.exitProcess

class DefaultInterpreter : Interpreter {
   private val tokenizer = DefaultTokenizer()
   private val parser = RootParser()
   private val evaluator = RootEvaluator()

   fun run(
      code: String,
      printTokens: Boolean = false,
      printAst: Boolean = false,
      dryRun: Boolean = false
   ): Environment {
      val lines = code.split("\n")
      return run(lines, printTokens, printAst, dryRun)
   }

   private fun run(lines: List<String>, printTokens: Boolean, printAst: Boolean, dryRun: Boolean): Environment {
      try {
         return tryToRun(lines, printTokens, printAst, dryRun)
      } catch (e: SmnpException) {
         printError(e)
         exitProcess(1)
      }
   }

   private fun printError(e: SmnpException) {
      err.println(e.friendlyName)
      err.println(e.message)
   }

   private fun tryToRun(lines: List<String>, printTokens: Boolean, printAst: Boolean, dryRun: Boolean): Environment {
      val environment = createEnvironment()
      val tokens = tokenizer.tokenize(lines)
      val ast = parser.parse(tokens)

      if (printTokens) println(tokens)
      if (printAst) ast.node.pretty()

      if (!dryRun) {
         val result = evaluator.evaluate(ast.node, environment)

         if (result.result == EvaluationResult.FAILED) {
            throw RuntimeException("Evaluation failed")
         }
      }

      return environment
   }

   private fun createEnvironment(): Environment {
      val environment = DefaultEnvironment()
      environment.loadModule("smnp.lang")

      return environment
   }

   fun run(file: File, printTokens: Boolean = false, printAst: Boolean = false, dryRun: Boolean = false): Environment {
      val lines = file.readLines()
      return run(lines, printTokens, printAst, dryRun)
   }

   override fun run(code: String) = run(code, printTokens = false, printAst = false, dryRun = false)
}