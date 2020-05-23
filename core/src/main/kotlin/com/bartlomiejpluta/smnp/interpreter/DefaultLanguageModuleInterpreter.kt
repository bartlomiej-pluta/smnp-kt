package com.bartlomiejpluta.smnp.interpreter

import com.bartlomiejpluta.smnp.dsl.ast.parser.RootParser
import com.bartlomiejpluta.smnp.dsl.token.tokenizer.DefaultTokenizer
import com.bartlomiejpluta.smnp.environment.DefaultEnvironment
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.evaluator.Evaluator
import com.bartlomiejpluta.smnp.evaluation.evaluator.ExtendEvaluator
import com.bartlomiejpluta.smnp.evaluation.evaluator.FunctionDefinitionEvaluator
import com.bartlomiejpluta.smnp.evaluation.model.enumeration.EvaluationResult

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