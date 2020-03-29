package io.smnp.interpreter

import io.smnp.dsl.ast.parser.RootParser
import io.smnp.dsl.token.tokenizer.DefaultTokenizer
import io.smnp.environment.DefaultEnvironment
import io.smnp.environment.Environment
import io.smnp.evaluation.evaluator.Evaluator
import io.smnp.evaluation.evaluator.ExtendEvaluator
import io.smnp.evaluation.evaluator.FunctionDefinitionEvaluator
import io.smnp.evaluation.model.enumeration.EvaluationResult

class DefaultLanguageModuleInterpreter : LanguageModuleInterpreter {
   private val tokenizer = DefaultTokenizer()
   private val parser = RootParser()
   private val evaluator = Evaluator.repeat(
      Evaluator.assert(Evaluator.oneOf(
         FunctionDefinitionEvaluator(),
         ExtendEvaluator()
      ), "function definition or extend statement")
   )

   override fun run(code: String, source: String): Environment {
      val lines = code.split("\n")
      val tokens = tokenizer.tokenize(lines, source)
      val ast = parser.parse(tokens)

      val environment = DefaultEnvironment()
      val result = evaluator.evaluate(ast.node, environment)

      if (result.result == EvaluationResult.FAILED) {
         throw RuntimeException("Evaluation failed")
      }

      return environment
   }
}