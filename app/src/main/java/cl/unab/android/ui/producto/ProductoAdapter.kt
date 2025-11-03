package cl.unab.android.ui.producto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.unab.android.R
import cl.unab.android.data.model.Producto

class ProductoAdapter(
    private var productos: List<Producto>,
    private val onDelete: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNombre: TextView = v.findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = v.findViewById(R.id.tvDescripcion)
        val tvPrecio: TextView = v.findViewById(R.id.tvPrecio)
        val btnEliminar: Button = v.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = productos[position]
        holder.tvNombre.text = p.nombre
        holder.tvDescripcion.text = p.descripcion
        holder.tvPrecio.text = "$${p.precio}"
        holder.btnEliminar.setOnClickListener { onDelete(p) }
    }

    override fun getItemCount() = productos.size

    fun actualizar(lista: List<Producto>) {
        productos = lista
        notifyDataSetChanged()
    }
}
