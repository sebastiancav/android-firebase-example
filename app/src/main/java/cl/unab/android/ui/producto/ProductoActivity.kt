package cl.unab.android.ui.producto

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cl.unab.android.data.model.Producto
import cl.unab.android.databinding.ActivityProductoBinding

class ProductoActivity : AppCompatActivity() {

    private lateinit var b: ActivityProductoBinding
    private val vm: ProductoViewModel by viewModels()
    private lateinit var adapter: ProductoAdapter
    private var productoSeleccionado: Producto? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProductoBinding.inflate(layoutInflater)
        setContentView(b.root)

        // Configurar RecyclerView
        adapter = ProductoAdapter(
            emptyList(),
            onDelete = { vm.eliminarProducto(it.id) },
            onUploadPdf = {
                productoSeleccionado = it
                pdfLauncher.launch("application/pdf")
            },
            onViewPdf = { producto ->
                producto.fichaUrl?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                } ?: run {
                    Toast.makeText(this, "Este producto no tiene ficha PDF", Toast.LENGTH_SHORT).show()
                }
            }
        )
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

    private val pdfLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null && productoSeleccionado != null) {
            vm.subirFicha(uri, productoSeleccionado!!)
        }
    }


}
