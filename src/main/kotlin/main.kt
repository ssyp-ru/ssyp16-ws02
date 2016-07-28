fun main(args: Array<String>){
    val par = Parser()
    //val array = par.petoohTranslator("Morning copy Ke kudah Evening Ko Kud Ko Kukarek kud Ko Ko Ko Kudah PAR copy Kukarek ")
    val array = par.petoohTranslator("""Morning sum KeKe
    kudah kudah
        Kud
            kO Kudah
            Ko kudah
        kud
        Kudah
Evening
Morning copy Ke
    kudah
Evening
Morning sumAll Ke
    kudah kO
    Kud
	Kudah
	PAR sumAll
	Kudah
	PAR sum
	Kudah
    kud kudah Ko
Evening
kukarek Kudah
PAR sumAll Kukarek""")
    val inter = NotSoKovalInterpreter()
    inter.iterpret(array)
}
