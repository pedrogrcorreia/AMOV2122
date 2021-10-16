package pt.isec.a21280304.aula1

object Objeto {
    private var _valor = 1000

    init {
        _valor = 100000
    }

    val valor : Int
        get(){
            _valor--
            return _valor
        }
}