class PetoohFunction(val name: String, val params: Int, val code: Array<Token>) {
    fun run(arr: Array<Byte>, cur: Int): Byte {
        val interpreter = Interpreter()
        var arrayOfParams = Array<Byte>(params, { 0 })
        for(i in 0..params - 1){
            arrayOfParams[i] = arr[cur - 1 - i]
        }
        interpreter.pushValues(arrayOfParams)
        // TODO: fix below
//        interpreter.interpret(code)
//        return interpreter.getReturnValue()
        return 42
    }
}