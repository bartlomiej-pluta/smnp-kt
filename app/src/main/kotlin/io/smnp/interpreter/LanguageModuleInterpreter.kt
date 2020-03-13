package io.smnp.interpreter

import io.smnp.dsl.ast.parser.RootParser
import io.smnp.dsl.token.tokenizer.DefaultTokenizer
import io.smnp.environment.DefaultEnvironment
import io.smnp.environment.Environment
import io.smnp.evaluation.evaluator.Evaluator
import io.smnp.evaluation.evaluator.ExtendEvaluator
import io.smnp.evaluation.evaluator.FunctionDefinitionEvaluator
import io.smnp.evaluation.model.enumeration.EvaluationResult
import io.smnp.type.module.Module

class LanguageModuleInterpreter : Interpreter {
   private var rootModule = Module.create("<root>")
   private val tokenizer = DefaultTokenizer()
   private val parser = RootParser()
   private val evaluator = Evaluator.repeat(
      Evaluator.assert(Evaluator.oneOf(
         FunctionDefinitionEvaluator(),
         ExtendEvaluator()
      ), "function definition or extend statement")
   )

   override fun updateRootModule(newRootModule: Module) {
      rootModule = newRootModule
   }

   override fun run(code: String) {
      val lines = code.split("\n")
      val tokens = tokenizer.tokenize(lines)
      val ast = parser.parse(tokens)

      val environment = createEnvironment()
      val result = evaluator.evaluate(ast.node, environment)

      if (result.result == EvaluationResult.FAILED) {
         throw RuntimeException("Evaluation failed")
      }
   }

   private fun createEnvironment(): Environment {
      val environment = DefaultEnvironment(rootModule)
      environment.loadModule("smnp.lang")

      return environment
   }
}