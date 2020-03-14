package io.smnp.cli.model.entity

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import io.smnp.cli.model.enumeration.ModulesPrintMode
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
   val code by parser.storing("-c", "--code", help = "inline code to be executed").default<String?>(null)
   val file by parser.positional("SOURCE", help = "file with SMNP language code to be executed") { File(this) }.default<File?>(null)
}