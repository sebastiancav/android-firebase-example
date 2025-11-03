package cl.unab.android.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import cl.unab.android.data.model.Producto
import kotlinx.coroutines.tasks.await

class ProductoRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("productos") // nombre en Firestore

    private val libros = db.collection("libros")

    suspend fun getAll(): List<Producto> {
        val snapshot = collection.get().await()
        return snapshot.documents.mapNotNull {
            it.toObject(Producto::class.java)?.apply { id = it.id }
        }
    }

    suspend fun add(producto: Producto) {
        collection.add(producto).await()
    }

    suspend fun update(producto: Producto) {
        if (producto.id.isNotEmpty()) {
            collection.document(producto.id).set(producto).await()
        }
    }

    suspend fun delete(id: String) {
        collection.document(id).delete().await()
    }
}