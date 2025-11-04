package cl.unab.android.data.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import cl.unab.android.data.model.Producto
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ProductoRepository {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference
    private val collection = db.collection("productos") // nombre en Firestore

    suspend fun uploadPdf(pdfUri: Uri): String {
        val nombreArchivo = "fichas/${UUID.randomUUID()}.jpeg"
        val ref = storage.child(nombreArchivo)
        ref.putFile(pdfUri).await()
        return ref.downloadUrl.await().toString()
    }

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