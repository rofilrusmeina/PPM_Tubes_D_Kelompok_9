package com.example.unpas.k9_mobile_perkuliahan.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unpas.k9_mobile_perkuliahan.model.Matkul
import com.example.unpas.k9_mobile_perkuliahan.repositories.MatkulRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatkulViewModel @Inject constructor(private val matkulRepository: MatkulRepository) : ViewModel()
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
    private val _list: MutableLiveData<List<Matkul>> =
        MutableLiveData()
    val list: LiveData<List<Matkul>> get() = _list
    suspend fun loadItems() {
        _isLoading.postValue(true)
        matkulRepository.loadItems(onSuccess = {
            _isLoading.postValue(false)
            _list.postValue(it)
        }, onError = { list, message ->
            _toast.postValue(message)
            _isLoading.postValue(false)
            _list.postValue(list)
        })
    }
    suspend fun insert(
            kode : String,
            nama : String,
            sks : Int,
            praktikum : Int,
            deskripsi : String
        ){
        _isLoading.postValue(true)
        matkulRepository.insert(
            kode, nama, sks, praktikum, deskripsi,
            onError = { item, message ->
                _toast.postValue(message)
                _isLoading.postValue(false)
            }, onSuccess = {
                _isLoading.postValue(false)
                _success.postValue(true)
            }
        )
    }

    suspend fun loadItem(id: String, onSuccess: (Matkul?) ->
    Unit) {
        val item = matkulRepository.find(id)
        onSuccess(item)
    }
    suspend fun update(
            id: String,
            kode : String,
            nama : String,
            sks : Int,
            praktikum : Int,
            deskripsi : String
        ){
        _isLoading.postValue(true)
        matkulRepository.update(
            id, kode, nama, sks, praktikum, deskripsi,
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
        matkulRepository.delete(id, onError = { message ->
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