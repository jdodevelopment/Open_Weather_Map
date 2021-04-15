package ar.com.jdodevelopment.weather.ui.wheather.forecast

import androidx.lifecycle.*
import ar.com.jdodevelopment.weather.commons.Consts
import ar.com.jdodevelopment.weather.commons.network.Resource
import ar.com.jdodevelopment.weather.data.model.City
import ar.com.jdodevelopment.weather.data.model.ForecastItem
import ar.com.jdodevelopment.weather.data.service.ForecastService
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val service: ForecastService
) : ViewModel() {


    private val _city = MutableLiveData<City>(savedStateHandle.get(Consts.BundleKeys.CITY))
    val city: LiveData<City> get() = _city

    private val _listResource = MutableLiveData<Resource<List<ForecastItem>>>()
    val listResource: LiveData<Resource<List<ForecastItem>>> get() = _listResource


    fun consumeData() {
        viewModelScope.launch {
            _listResource.value = (Resource.Loading())
            try {
                val response = service.findByCityId(city.value!!.id)
                if (response.isSuccessful) {
                    val list = response.body()!!.list
                    _listResource.value = Resource.Success(list)
                    return@launch
                }
                val message = getErrorMessage(response)
                _listResource.value = (Resource.Error(message))
            } catch (t: Throwable) {
                t.printStackTrace()
                _listResource.value = (Resource.Error(t.message ?: "Se ha producido un error inesperado"))
            }
        }
    }


    private fun getErrorMessage(response: Response<*>): String {
        val responseBody = response.errorBody() ?: return "No hay información disponible."
        return try {
            val gson = Gson()
            val json = gson.fromJson(responseBody.string(), JsonObject::class.java)
            json["message"].asString
        } catch (e: IOException) {
            e.printStackTrace()
            "No hay información disponible."
        }
    }


}