package prot3ct.workit.models

import prot3ct.workit.models.base.LocationContract

class Location : LocationContract {
    private var lat = 0.0
    private var lng = 0.0
    override fun getLat(): Double {
        return lat
    }

    override fun setLat(lat: Double) {
        this.lat = lat
    }

    override fun getLng(): Double {
        return lng
    }

    override fun setLng(lng: Double) {
        this.lng = lng
    }
}