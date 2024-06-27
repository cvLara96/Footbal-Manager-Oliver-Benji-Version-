package com.example.footballmanager_cvl.ui.historic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.footballmanager_cvl.R;
import com.example.footballmanager_cvl.ui.home.Jugador;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

//(*)ESTA CLASE HEREDA DE RecyclerView.Adapter
//ESTO NOS SACARA VARIOS ERRORES
// - EN PRIMER LUGAR NOS HARA IMPLEMENTAR LA CLASE ViewHolderDatos
// - LUEGO NOS HARA IMPLEMENTAR LOS METODOS DE LA CLASE PADRE
// - LUEGO NOS HARA HACER QUE INDIQUEMOS QUE LA CLASE ViewHolderDatos
//EXTIENDE DE RecyclerView.ViewHolder
// - POR ULTIMO NOS HARA IMPLEMENTAR EL CONSTRUCTOR DE ViewHolderDatos
public class MyAdapterHistoric extends RecyclerView.Adapter<MyAdapterHistoric.ViewHolderDatos> {

    //ESTE ADAPTADOR RECIBIRA UNA LISTA DE DATOS, DE MANERA QUE CREAMOS UN ARRAYLIST
    ArrayList<Jugador> listaJugadores;

    //CREAMOS UN LAYOUTINFLATER QUE INFLARA LA VISTA QUE TENDRA QUE MOSTRAR (la definida en list_jugadores.xml):
    private LayoutInflater inflater;

    //CREAMOS UN CONTEXT PARA INDICAR DE QUE CLASE ESTAMOS LLAMANDO ESTE ADAPTADOR
    private Context context;


    //CREAREMOS EL CONSTRUCTOR
    public MyAdapterHistoric(ArrayList<Jugador> listaJugadores, Context context) {

        this.listaJugadores = listaJugadores;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    //ESTE METODO ENLAZA EL ADAPTADOR CON EL FICHERO itemsrecycler.xml
    public MyAdapterHistoric.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //DE MANERA QUE AQUI GENERAREMOS UN VIEW INFLADO CON ESE LAYOUT:
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_historico, null, false);

        return new MyAdapterHistoric.ViewHolderDatos(view);
    }

    //ESTE METODO SE ENCARGARA DE ESTABLECER LA COMUNICACION ENTRE NUESTRO ADAPTADOR Y
    //LA CLASE ViewHolderDatos
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition));
        holder.asignarDatos(listaJugadores.get(position));
    }

    //ESTE METODO RETORNARA EL TAMAÑO DE LA LISTA
    @Override
    public int getItemCount() {
        return listaJugadores.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        //AQUI REFERENCIAMOS LOS ELEMENTOS QUE TENDRA EL RECYCLERVIEW
        TextView textoNombre;
        TextView textoPosicion;
        ImageView imagenJugador;
        View view;
        CardView cardView;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.relativePulsable);
            //PARA REFERENCIARLO USAMOS EL itemView:
            textoNombre = itemView.findViewById(R.id.textNombre);
            textoPosicion = itemView.findViewById(R.id.textPosicion);
            imagenJugador = itemView.findViewById(R.id.imageCard);
            cardView = itemView.findViewById(R.id.CardView);
        }

        public void asignarDatos(Jugador datosJugador) {

            // Obtenemos una referencia a la imagen que deseas descargar
            StorageReference imageRef = FirebaseStorage.getInstance().getReference().child(datosJugador.getFotoJugador());

            //Obtenemos la URL de descarga
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                Glide.with(context)
                        .load(uri)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))//Cachea la imagen
                        .into(imagenJugador);//Carga la imagen en un ImageView utilizando Glide
            }).addOnFailureListener(e -> {
                Log.e("Firebase Storage", "Error al obtener la URL", e);
            });

            //imagenJugador.setImageResource(datosJugador.getFotoJugador());
            textoNombre.setText(datosJugador.getNombre());
            textoPosicion.setText(datosJugador.getPosicion());
        }
    }

//--> UNA VEZ CREADO EL ADAPTADOR PASAREMOS AL PASO 13 EN MAINACTIVITY2
}
