package com.example.foodstore.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.example.foodstore.R;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.Annotation;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

public class AboutFragment extends Fragment {

    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
    }

//    private void addAnnotationToMap() {
//// Create an instance of the Annotation API and get the PointAnnotationManager.
//        bitmapFromDrawableRes(getActivity(), R.drawable.red_marker) {
//            Annotation annotationApi = (Annotation) mapView.getViewAnnotationManager();
//            PointAnnotationManager pointAnnotationManager = annotationApi.(mapView);
//// Set options for the resulting symbol layer.
//            PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
//// Define a geographic coordinate.
//                    .withPoint(Point.fromLngLat(51.125980,71.433153))
//// Specify the bitmap you assigned to the point annotation
//// The bitmap will be added to map style automatically.
//                    .withIconImage();
//// Add the resulting pointAnnotation to the map.
//            pointAnnotationManager.create(pointAnnotationOptions);
//        }
//    }
//
//    private void bitmapFromDrawableRes(Context context, @DrawableRes Integer resourceId){
//        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId));
//    }
//
//    private Bitmap convertDrawableToBitmap(Drawable sourceDrawable) {
//        if (sourceDrawable == null) {
//            return null;
//        }
//        else if (sourceDrawable == BitmapDrawable) {
//            return sourceDrawable.bitmap;
//        } else {
//// copying drawable object to not manipulate on the same reference
//            Drawable.ConstantState constantState = sourceDrawable.getConstantState(){
//                return null;
//            }
//
//            Drawable drawable = constantState.newDrawable().mutate();
//            Bitmap bitmap = Bitmap.createBitmap(
//                    drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
//                    Bitmap.Config.ARGB_8888
//            );
//            Canvas canvas = new Canvas(bitmap);
//            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//            drawable.draw(canvas);
//            return bitmap;
//        }
//    }
}
