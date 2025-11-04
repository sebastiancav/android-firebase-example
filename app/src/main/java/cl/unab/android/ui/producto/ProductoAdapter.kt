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
    private val onDelete: (Producto) -> Unit,
    private val onUploadPdf: (Producto) -> Unit,
    private val onViewPdf: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNombre: TextView = v.findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = v.findViewById(R.id.tvDescripcion)
        val tvPrecio: TextView = v.findViewById(R.id.tvPrecio)
        val btnEliminar: Button = v.findViewById(R.id.btnEliminar)
        val btnSubirPdf: Button = v.findViewById(R.id.btnSubirPdf)
        val btnVerPdf: Button = v.findViewById(R.id.btnVerPdf)
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
        holder.btnSubirPdf.setOnClickListener { onUploadPdf(p) }

        // 🔹 Si tiene URL de ficha, activa el botón
        if (p.fichaUrl != null) {
            holder.btnVerPdf.isEnabled = true
            holder.btnVerPdf.alpha = 1f
        } else {
            holder.btnVerPdf.isEnabled = false
            holder.btnVerPdf.alpha = 0.5f
        }

        holder.btnVerPdf.setOnClickListener { onViewPdf(p) }
    }

    override fun getItemCount() = productos.size

    fun actualizar(lista: List<Producto>) {
        productos = lista
        notifyDataSetChanged()
    }
}
