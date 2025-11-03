package cl.unab.android.ui.producto

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cl.unab.android.data.model.Producto
import cl.unab.android.databinding.ActivityProductoBinding

class ProductoActivity : AppCompatActivity() {

    private lateinit var b: ActivityProductoBinding
    private val vm: ProductoViewModel by viewModels()
    private lateinit var adapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProductoBinding.inflate(layoutInflater)
        setContentView(b.root)

        // Configurar RecyclerView
        adapter = ProductoAdapter(emptyList()) { producto ->
            vm.eliminarProducto(producto.id)
        }
        b.rvProductos.layoutManager = LinearLayoutManager(this)
        b.rvProductos.adapter = adapter

        // Botón agregar
        b.btnAgregar.setOnClickListener {
            val nombre = b.etNombre.text.toString()
            val descripcion = b.etDescripcion.text.toString()
            val precio = b.etPrecio.text.toString().toDoubleOrNull() ?: 0.0

            val producto = Producto(
                nombre = nombre,
                descripcion = descripcion,
                precio = precio
            )
            vm.agregarProducto(producto)

            // Limpia los campos para que se vea bonito en la demo
            b.etNombre.text?.clear()
            b.etDescripcion.text?.clear()
            b.etPrecio.text?.clear()
        }

        // Observar cambios en la lista
        vm.productos.observe(this) { lista ->
            adapter.actualizar(lista)
        }

        // Cargar al inicio
        vm.cargarProductos()
    }
}
