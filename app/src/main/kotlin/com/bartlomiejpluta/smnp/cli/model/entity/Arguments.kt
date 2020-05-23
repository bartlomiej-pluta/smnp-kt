package com.bartlomiejpluta.smnp.cli.model.entity

import com.bartlomiejpluta.smnp.cli.model.enumeration.ModulesPrintMode
import com.bartlomiejpluta.smnp.preset.PresetProvider
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.io.File

class Arguments(parser: ArgParser) {
   val printTokens by parser.flagging("--tokens", help = "print tokens").default(false)
   val printAst by parser.flagging("--ast", help = "print abstract syntax tree").default(false)
   val dryRun by parser.flagging("--dry-run", help = "don't evaluate parsed code").default(false)
   val availableModules by parser.flagging("--modules-all", help = "print all available modules").default(false)
   val loadedModules by parser.storing(
      "--modules",
      argName = "MODE",
      help = "print modules loaded by executed script. Allowed values for MODE are: list | tree | content. The 'list' option " +
         "prints loaded modules as list of canonical names of each one. The 'tree' option organises modules into the tree model " +
         "and then prints them. The 'content' option is the same as tree, however, prints also contained functions and methods."
   ) { ModulesPrintMode.valueOf(this.toUpperCase()) }.default<ModulesPrintMode?>(null)
   val parameters by parser.adding(
      "-P", "--parameter", help = "define parameter to be consumed in script. Default syntax for parameter " +
         "is 'key=value' (whitespaces around '=' are not allowed). In this case value will be of string type. " +
         "However, it is also possible to pass parameter without any value. In this case, value will be of bool type. " +
         "The parameters are available through global-accessible variable of map type and of '__param__' name."
   ) {
      this.split("=").let {
         when (it.size) {
            1 -> it[0] to true
            else -> it[0] to it.subList(1, it.size).joinToString("=")
         }
      }
   }
   val preset by parser.storing(
      "-p",
      "--preset",
      argName = "NAME",
      help = "load preset by name and execute it. Preset is a predefined snippet of SMNP code that allows user to " +
         "perform some useful action without having to script it himself. Presets are located in SMNP executable JAR " +
         "file. Available presets: ${PresetProvider.presets.joinToString()}"
   ).default<String?>(null)

   val code by parser.storing("-c", "--code", help = "inline code to be executed").default<String?>(null)
   val file by parser.positional(
      "SOURCE",
      help = "file with SMNP language code to be executed"
   ) { File(this) }.default<File?>(null)
}