package io.smnp.type.module

import io.smnp.callable.function.Function
import io.smnp.callable.method.Method
import io.smnp.type.model.Value

class Module(
    val name: String,
    functions: List<Function> = emptyList(),
    methods: List<Method> = emptyList(),
    children: List<Module> = emptyList()
) {
    private var parent: Module? = null
    private val children = mutableListOf<Module>()
    private val functions = functions.toMutableList()
    private val methods = methods.toMutableList()

    init {
        children.forEach { addSubmodule(it) }
        functions.forEach { it.module = this }
        methods.forEach { it.module = this }
    }

    fun addSubmodule(module: Module) {
        module.parent = this
        children.indexOfFirst { it.name == module.name }.let {
            if (it > -1) {
                children[it] = children[it].merge(module)
            } else {
                children.add(module)
            }
        }
    }

    fun addFunction(function: Function) {
        function.module = this
        functions.add(function)
    }

    fun addMethod(method: Method) {
        method.module = this
        methods.add(method)
    }

    val canonicalName: String
        get() {
            val modules = mutableListOf(this)
            var par = parent
            while (par != null) {
                modules.add(par)
                par = par.parent
            }

            return modules.reversed().joinToString(".") { it.name }
        }

    fun merge(module: Module): Module {
        if (name != module.name) {
            return this
        }

        val functions = functions + module.functions
        val methods = methods + module.methods

        val commonAndMyChildren = children.map { child ->
            module.children.find { it.name == child.name }?.merge(child) ?: child
        }

        val moduleChildren = module.children.filter { child -> children.none { it.name == child.name } }

        return Module(
            name,
            functions,
            methods,
            (commonAndMyChildren + moduleChildren).toMutableList()
        )
    }

    fun findFunction(name: String): List<Function> {
        return functions.filter { it.name == name } + children.flatMap { it.findFunction(name) }
    }

    fun findMethod(value: Value, name: String): List<Method> {
        return methods.filter { it.name == name && it.verifyType(value) } + children.flatMap {
            it.findMethod(
                value,
                name
            )
        }
    }

    override fun toString() = name

    fun pretty(printContent: Boolean = false, prefix: String = "", last: Boolean = true, first: Boolean = true) {
        var newPrefix = prefix
        var newLast = last

        println(newPrefix + (if (first) "" else if (newLast) "└─ " else "├─ ") + name)
        newPrefix += if (newLast) "   " else "│  "
        if (printContent) {
            val contentPrefix = newPrefix + if (children.isNotEmpty()) "|" else ""
            for ((index, function) in functions.withIndex()) {
                println(contentPrefix + (if (index == functions.size - 1 && methods.isEmpty()) "└ " else "├ ") + "${function.name}()")
            }

            for ((index, method) in methods.withIndex()) {
                println(contentPrefix + (if (index == methods.size - 1) "└ " else "├ ") + "${method.typeMatcher}.${method.name}()")
            }
        }

        for ((index, child) in children.withIndex()) {
            newLast = index == children.size - 1
            child.pretty(printContent, newPrefix, newLast, false)
        }
    }

    companion object {
        fun create(
            path: String,
            functions: List<Function> = emptyList(),
            methods: List<Method> = emptyList(),
            children: List<Module> = emptyList()
        ): Module {
            val modules = path.split(".")
            if (modules.isEmpty()) {
                return Module(path, functions, methods, children)
            }

            val root = modules.map { Module(it) }.reduceRight { m, n -> m.addSubmodule(n); m }

            var youngest = root
            while (youngest.children.isNotEmpty()) {
                youngest = youngest.children[0]
            }

            youngest.functions.addAll(functions)
            youngest.methods.addAll(methods)
            youngest.children.addAll(children)
            youngest.functions.forEach { it.module = youngest }
            youngest.methods.forEach { it.module = youngest }

            return root
        }
    }
}