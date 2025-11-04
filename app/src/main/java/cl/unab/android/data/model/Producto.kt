package cl.unab.android.data.model

data class Producto(
    var id: String = "",
    var nombre: String = "",
    var descripcion: String = "",
    var precio: Double = 0.0,
    var fichaUrl: String? = null // URL del PDF en Storage
)