package cl.unab.android.ui.producto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.unab.android.data.model.Producto
import cl.unab.android.data.repository.ProductoRepository
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    private val repo = ProductoRepository()

    private val _productos = MutableLiveData<List<Producto>>()
    val productos: LiveData<List<Producto>> = _productos

    fun cargarProductos() {
        viewModelScope.launch {
            _productos.value = repo.getAll()
        }
    }

    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            repo.add(producto)
            cargarProductos()
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            repo.update(producto)
            cargarProductos()
        }
    }

    fun eliminarProducto(id: String) {
        viewModelScope.launch {
            repo.delete(id)
            cargarProductos()
        }
    }
}
