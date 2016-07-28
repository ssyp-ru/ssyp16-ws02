fun main(args: Array<String>){
    val compiler = TokenCompiler()
    if(args.size == 2)
        compiler.compile(args[0], args[1])
    else
        println("Bad arguments")
}
