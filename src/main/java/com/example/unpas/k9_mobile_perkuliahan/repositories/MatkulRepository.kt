package com.example.unpas.k9_mobile_perkuliahan.repositories

import com.benasher44.uuid.uuid4
import com.example.unpas.k9_mobile_perkuliahan.model.Matkul
import com.example.unpas.k9_mobile_perkuliahan.networks.MatkulApi
import com.example.unpas.k9_mobile_perkuliahan.persistences.MatkulDao
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import javax.inject.Inject

class MatkulRepository @Inject constructor(
    private val api: MatkulApi,
    private val dao: MatkulDao
) : Repository {
    suspend fun loadItems(
        onSuccess: (List<Matkul>) -> Unit,
        onError: (List<Matkul>, String) -> Unit
    ) {
        val list: List<Matkul> = dao.getList()
        api.all()
// handle the case when the API request gets a success response.
            .suspendOnSuccess {
                data.whatIfNotNull {
                    it.data?.let { list ->
                        dao.insertAll(list)
                        val items: List<Matkul> = dao.getList()
                        onSuccess(items)
                    }
                }
            }
// handle the case when the API request gets an error response.
// e.g. internal server error.
            .suspendOnError {
                onError(list, message())
            }
// handle the case when the API request gets an exception response.
// e.g. network connection error.
            .suspendOnException {
                onError(list, message())
            }
    }
    suspend fun insert(
        kode: String,
        nama: String,
        sks: Int,
        praktikum : Int,
        deskripsi : String,
        onSuccess: (Matkul) -> Unit,
        onError: (Matkul?, String) -> Unit
    ) {
        val id = uuid4().toString()
        val item = Matkul(id, kode, nama, sks, praktikum, deskripsi)
        dao.insertAll(item)
        api.insert(item)
// handle the case when the API request gets a success response.
            .suspendOnSuccess {
                onSuccess(item)
            }
// handle the case when the API request gets an rror response.
// e.g. internal server error.
            .suspendOnError {
                onError(item, message())
            }
// handle the case when the API request gets an xception response.
// e.g. network connection error.
            .suspendOnException {
                onError(item, message())
            }
    }
    suspend fun update(
        id : String,
        kode : String,
        nama : String,
        sks : Int,
        praktikum : Int,
        deskripsi : String,
        onSuccess: (Matkul) -> Unit,
        onError: (Matkul?, String) -> Unit
    ) {
        val item = Matkul(id, kode, nama, sks, praktikum, deskripsi)
        dao.insertAll(item)
        api.update(id, item)
// handle the case when the API request gets a success response.
            .suspendOnSuccess {
                onSuccess(item)
            }
// handle the case when the API request gets an rror response.
// e.g. internal server error.
            .suspendOnError {
                onError(item, message())
            }
// handle the case when the API request gets an exception response.
// e.g. network connection error.
            .suspendOnException {
                onError(item, message())
            }
    }

    suspend fun delete(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        dao.delete(id)
        api.delete(id)
// handle the case when the API request gets a uccess response.
            .suspendOnSuccess {
                data.whatIfNotNull {
                    onSuccess()
                }
            }
// handle the case when the API request gets an error response.
// e.g. internal server error.
            .suspendOnError {
                onError(message())
            }
// handle the case when the API request gets an exception response.
// e.g. network connection error.
            .suspendOnException {
                onError(message())
            }
    }

    suspend fun find(id: String) : Matkul? {
        return dao.find(id)
    }
}