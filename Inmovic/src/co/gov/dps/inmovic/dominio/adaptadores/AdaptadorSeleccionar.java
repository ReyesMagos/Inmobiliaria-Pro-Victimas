package co.gov.dps.inmovic.dominio.adaptadores;

import java.util.Random;

import co.gov.dps.inmovic.dominio.adaptadores.AdaptadorDestacados.ViewHolder;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorSeleccionar extends ArrayAdapter<String> {

	Activity context;
	ViewHolder holder;
	String [] titulo;

	public AdaptadorSeleccionar(Activity context, String[] x) {
		super(context, R.layout.contenido_seleccionar_inmueble_venta, x);
		this.context = context;
		titulo= x;

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;

		Random random = new Random();

		if (item == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(
					R.layout.contenido_seleccionar_inmueble_venta, null);
			holder.txtInmuebles= (TextView) item.findViewById(R.id.txtInmuebles);
			holder.txtInmuebles.setText(titulo[position]);

			item.setTag(holder);
		} else {
			holder = (ViewHolder) item.getTag();
			holder.txtInmuebles.setText(titulo[position]);
		}

		return (item);

	}

	static class ViewHolder {
		TextView txtInmuebles;
		
	}

}
