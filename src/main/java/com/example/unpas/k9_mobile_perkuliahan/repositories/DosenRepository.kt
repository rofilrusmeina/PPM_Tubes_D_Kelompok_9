package com.example.unpas.k9_mobile_perkuliahan.repositories

import com.benasher44.uuid.uuid4
import com.example.unpas.k9_mobile_perkuliahan.model.Dosen
import com.example.unpas.k9_mobile_perkuliahan.model.Matkul
import com.example.unpas.k9_mobile_perkuliahan.networks.DosenApi
import com.example.unpas.k9_mobile_perkuliahan.persistences.DosenDao
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import javax.inject.Inject

class DosenRepository @Inject constructor(
    private val api: DosenApi,
    private val dao: DosenDao
) : Repository {
    suspend fun loadItems(
        onSuccess: (List<Dosen>) -> Unit,
        onError: (List<Dosen>, String) -> Unit
    ) {
        val list: List<Dosen> = dao.getList()
        api.all()
// handle the case when the API request gets a success response.
            .suspendOnSuccess {
                data.whatIfNotNull {
                    it.data?.let { list ->
                        dao.insertAll(list)
                        val items: List<Dosen> = dao.getList()
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
        nidn : String,
        nama : String,
        gelar_depan : String,
        gelar_belakang : String,
        pendidikan : String,

        onSuccess: (Dosen) -> Unit,
        onError: (Dosen?, String) -> Unit
    ) {
        val id = uuid4().toString()
        val item = Dosen(id,nidn, nama, gelar_depan, gelar_belakang, pendidikan)
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
        nidn : String,
        nama : String,
        gelar_depan : String,
        gelar_belakang : String,
        pendidikan : String,

        onSuccess: (Dosen) -> Unit,
        onError: (Dosen?, String) -> Unit
    ) {
        val item = Dosen(id, nidn, nama, gelar_depan, gelar_belakang, pendidikan)
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

    suspend fun find(id: String) : Dosen? {
        return dao.find(id)
    }
}