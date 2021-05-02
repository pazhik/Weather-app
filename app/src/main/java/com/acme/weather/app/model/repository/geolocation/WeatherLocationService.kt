package com.acme.weather.app.model.repository.geolocation

import android.location.Geocoder
import android.os.Debug
import com.acme.weather.app.model.api.Location
import timber.log.Timber
import java.io.IOException
import java.lang.IllegalArgumentException
import javax.inject.Inject

class WeatherLocationService @Inject constructor(val geoCoder: Geocoder) {

    fun locationForZip(zip: String) : Location? {
        return try {
            val address = geoCoder.getFromLocationName(zip, 1).firstOrNull()
            Timber.d(address?.locality)
            if (address == null) {
                Timber.e("Wrong argument")
                return null
            } else {
                Location(locationName = address.locality,
                        zip = address.postalCode,
                        longitude = address.longitude.toString(),
                        latitude = address.latitude.toString())
            }
        } catch (e: IOException) {
            Timber.e("Utility without Google Play")
            return null
        } catch (e: IllegalArgumentException) {
            Timber.e("IllegalArguments")
            return null
        }
    }

}