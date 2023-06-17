package com.example.unpas.k9_mobile_perkuliahan.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unpas.k9_mobile_perkuliahan.model.Dosen
import com.example.unpas.k9_mobile_perkuliahan.repositories.DosenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DosenViewModel @Inject constructor(private val dosenRepository: DosenRepository) : ViewModel()
{
    private val _isLoading: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _success: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val success: LiveData<Boolean> get() = _success
    private val _toast: MutableLiveData<String> =
        MutableLiveData()
    val toast: LiveData<String> get() = _toast
    private val _list: MutableLiveData<List<Dosen>> =
        MutableLiveData()
    val list: LiveData<List<Dosen>> get() = _list
    suspend fun loadItems() {
        _isLoading.postValue(true)
        dosenRepository.loadItems(onSuccess = {
            _isLoading.postValue(false)
            _list.postValue(it)
        }, onError = { list, message ->
            _toast.postValue(message)
            _isLoading.postValue(false)
            _list.postValue(list)
        })
    }
    suspend fun insert(
            nidn : String,
            nama : String,
            gelar_depan : String,
            gelar_belakang : String,
            pendidikan : String
        ){
        _isLoading.postValue(true)
        dosenRepository.insert(
            nidn, nama, gelar_depan, gelar_belakang, pendidikan,
            onError = { item, message ->
                _toast.postValue(message)
                _isLoading.postValue(false)
            }, onSuccess = {
                _isLoading.postValue(false)
                _success.postValue(true)
            }
        )
    }

    suspend fun loadItem(id: String, onSuccess: (Dosen?) ->
    Unit) {
        val item = dosenRepository.find(id)
        onSuccess(item)
    }
    suspend fun update(
            id: String,
            nidn : String,
            nama : String,
            gelar_depan : String,
            gelar_belakang : String,
            pendidikan : String
        ){
        _isLoading.postValue(true)
        dosenRepository.update(
            id, nidn, nama, gelar_depan, gelar_belakang, pendidikan,
            onError = { item, message ->
                _toast.postValue(message)
                _isLoading.postValue(false)
            }, onSuccess = {
                _isLoading.postValue(false)
                _success.postValue(true)
            }
        )
    }

    suspend fun delete(id: String) {
        _isLoading.postValue(true)
        dosenRepository.delete(id, onError = { message ->
            _toast.postValue(message)
            _isLoading.postValue(false)
            _success.postValue(true)
        }, onSuccess = {
            _toast.postValue("Data berhasil dihapus")
            _isLoading.postValue(false)
            _success.postValue(true)
        })
    }

}