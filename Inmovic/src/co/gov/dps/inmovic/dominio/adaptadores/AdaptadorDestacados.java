package co.gov.dps.inmovic.dominio.adaptadores;

import java.util.Random;

import co.gov.dps.inmovic.dominio.entidades.Destacado;
import co.gov.dps.inmovic.presentacion.actividades.destacados.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorDestacados extends ArrayAdapter<Destacado> {

	Activity context;
	private Destacado[] destacados;
	ViewHolder holder;

	public AdaptadorDestacados(Activity context, Destacado[] d) {
		super(context, R.layout.contenido_destacados, d);
		this.destacados = d;
		this.context = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		
		Random random = new Random();

		if (item == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(R.layout.contenido_destacados, null);

			holder = new ViewHolder();
			holder.tipo = (TextView) item.findViewById(R.id.tipo);
			holder.nombre = (TextView) item.findViewById(R.id.nombre);
			holder.canon = (TextView) item.findViewById(R.id.canon);
			holder.ubicacion = (TextView) item.findViewById(R.id.ubicacion);
			holder.imagen = (ImageView) item.findViewById(R.id.contactar);

			item.setTag(holder);
		} else {
			holder = (ViewHolder) item.getTag();
		}

		holder.tipo.setText(destacados[position].getTipo());
		holder.canon.setText(destacados[position].getCanon());
		holder.ubicacion.setText(destacados[position].getUbicacion());
		holder.nombre.setText(destacados[position].getNombre());
		
		if (destacados[position].getImagen() != null) {
			holder.imagen.setImageBitmap(destacados[position].getImagen());
		} else {
			holder.imagen.setImageResource(R.drawable.logo_unidad);
		}
		return (item);

	}
	
	
	

	static class ViewHolder {
		TextView tipo;
		TextView nombre;
		TextView canon;
		TextView ubicacion;
		ImageView imagen;
	}
}
