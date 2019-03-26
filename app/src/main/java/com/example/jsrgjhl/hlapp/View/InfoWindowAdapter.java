package com.example.jsrgjhl.hlapp.View;

import android.view.View;

import com.amap.api.maps.model.Marker;

public interface InfoWindowAdapter {
    View getInfoWindow(Marker marker);
    View getInfoContents(Marker marker);
}