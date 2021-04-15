package ar.com.jdodevelopment.weather.ui.wheather.detail

import androidx.lifecycle.*
import ar.com.jdodevelopment.weather.commons.network.Resource
import ar.com.jdodevelopment.weather.data.model.City
import ar.com.jdodevelopment.weather.data.model.Coord
import ar.com.jdodevelopment.weather.data.response.WeatherResponse
import ar.com.jdodevelopment.weather.data.service.WeatherService
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val service: WeatherService
) : ViewModel() {

    private val _city = MutableLiveData<City>()
    val city: LiveData<City> get() = _city

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> get() = _cities

    private val _listResource = MutableLiveData<Resource<WeatherResponse>>()
    val wheaterData: LiveData<Resource<WeatherResponse>> get() = _listResource

    init {
        // TODO: Consumir ciudades desde un servicio
        val cities = arrayListOf(
            City(3855244, "Gálvez", Coord(0.0,0.0), "", 0,0,0,0),
            City(3838583, "Rosario", Coord(0.0,0.0), "", 0,0,0,0),
            City(3836277, "Santa Fe", Coord(0.0,0.0), "", 0,0,0,0),
            City(3432043, "La Plata", Coord(0.0,0.0), "", 0,0,0,0),
            City(3860259, "Córdoba", Coord(0.0,0.0), "", 0,0,0,0),
        )
        _cities.value = cities
        _city.value = cities[0]
    }


    fun consumeData() {
        viewModelScope.launch {
            _listResource.value = (Resource.Loading())
            try {
                val city = _city.value!!
                val response = service.findByCityId(city.id)
                if (response.isSuccessful) {
                    _listResource.value = (Resource.Success(response.body()!!))
                    return@launch
                }
                val message = getErrorMessage(response)
                _listResource.value = (Resource.Error(message))
            } catch (t: Throwable) {
                t.printStackTrace()
                _listResource.value = (Resource.Error(t.message ?: "Se ha producio un error inesperado"))
            }
        }
    }

    fun setCity(city: City){
        _city.value = city
        consumeData()
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