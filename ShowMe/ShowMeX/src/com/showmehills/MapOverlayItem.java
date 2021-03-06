/*
    Copyright 2012 Nik Cain nik@showmehills.com
    
    This file is part of ShowMeHills.

    ShowMeHills is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ShowMeHills is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with ShowMeHills.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.showmehills;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MapOverlayItem extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;

	Paint strokePaint = new Paint();
	Paint textPaint = new Paint();
	
	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}
	@Override
    public void draw(android.graphics.Canvas canvas, MapView mapView, boolean shadow)
    {
        super.draw(canvas, mapView, false);

        if (shadow == false)
        {
            //cycle through all overlays
            for (int index = 0; index < mOverlays.size(); index++)
            {
                OverlayItem item = mOverlays.get(index);

                // Converts lat/lng-Point to coordinates on the screen
                GeoPoint point = item.getPoint();
                Point ptScreenCoord = new Point() ;
                mapView.getProjection().toPixels(point, ptScreenCoord);

                //show text to the right of the icon
                canvas.drawText(item.getTitle(), ptScreenCoord.x, ptScreenCoord.y+20, strokePaint);
                canvas.drawText(item.getTitle(), ptScreenCoord.x, ptScreenCoord.y+20, textPaint);
            }
        }
    }

	public MapOverlayItem(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;

	    textPaint.setARGB(255, 255, 255, 255);
	    textPaint.setTextAlign(Paint.Align.CENTER);
	    textPaint.setTypeface(Typeface.DEFAULT_BOLD);
		
	    strokePaint.setARGB(255, 0, 0, 0);
	    strokePaint.setTextAlign(Paint.Align.CENTER);
	    strokePaint.setTypeface(Typeface.DEFAULT_BOLD);
	    strokePaint.setStyle(Paint.Style.STROKE);
	    strokePaint.setStrokeWidth(4);

	    textPaint.setTextSize(25);
	    strokePaint.setTextSize(25);
	}
	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
}
