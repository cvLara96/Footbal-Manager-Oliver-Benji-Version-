package com.example.footballmanager_cvl.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.footballmanager_cvl.R;
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
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolderDatos> {

    //ESTE ADAPTADOR RECIBIRA UNA LISTA DE DATOS, DE MANERA QUE CREAMOS UN ARRAYLIST
    ArrayList<Jugador> listaJugadores;

    //CREAMOS UN LAYOUTINFLATER QUE INFLARA LA VISTA QUE TENDRA QUE MOSTRAR (la definida en list_jugadores.xml):
    private LayoutInflater inflater;

    //CREAMOS UN CONTEXT PARA INDICAR DE QUE CLASE ESTAMOS LLAMANDO ESTE ADAPTADOR
    private Context context;

    //CREAMOS UNA INSTANCIA DE HomeFragment
    HomeFragment homeFragment;

    //CREAMOS LO NECESARIO PARA SABER QUE POKEMON SE HA ELEGIDO
    RelativeLayout relativeLayout;
    TextView jugadorElegido;
    TextView posicionElegida;
    ImageView fotoJugadorElegido;
    ImageView favNoOK;
    ImageView favOK;
    String nombreJugador;
    String posicionJugador;
    String fotoJugador;

    //CREAREMOS EL CONSTRUCTOR
    public MyAdapter(ArrayList<Jugador> listaJugadores, Context context,HomeFragment homeFragment) {

        this.listaJugadores = listaJugadores;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    //ESTE METODO ENLAZA EL ADAPTADOR CON EL FICHERO itemsrecycler.xml
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //DE MANERA QUE AQUI GENERAREMOS UN VIEW INFLADO CON ESE LAYOUT:
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_jugadores, null, false);

        return new ViewHolderDatos(view);
    }

    //ESTE METODO SE ENCARGARA DE ESTABLECER LA COMUNICACION ENTRE NUESTRO ADAPTADOR Y
    //LA CLASE ViewHolderDatos
    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull ViewHolderDatos holder,  int position) {
        //UTILIZAREMOS EL holder Y CREAREMOS UN METODO LLAMADO asignarDatos QUE
        //RECIBIRA COMO PARAMETRO LA INFORMACION QUE QUEREMOS QUE MUESTRE:
        //EL METODO asignarDatos debera estar creado en la clase ViewHolderDatos
        //DE MANERA QUE PULSAREMOS SOBRE EL PARA QUE LO GENERE EN ESTA CLASE:
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition));
        holder.asignarDatos(listaJugadores.get(position));

        holder.imagenNoFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                relativeLayout = (RelativeLayout)v.getParent();
                jugadorElegido = relativeLayout.findViewById(R.id.textNombre);
                nombreJugador = jugadorElegido.getText().toString();
                posicionElegida = relativeLayout.findViewById(R.id.textPosicion);
                posicionJugador = posicionElegida.getText().toString();

                fotoJugador = listaJugadores.get(position).getFotoJugador();

                favNoOK = relativeLayout.findViewById(R.id.imageFav);
                favOK = relativeLayout.findViewById(R.id.imageFavOk);
                if(favNoOK.getVisibility()==View.VISIBLE){
                    favNoOK.setVisibility(View.INVISIBLE);
                    favOK.setVisibility(View.VISIBLE);
                }else{
                    favNoOK.setVisibility(View.VISIBLE);
                    favOK.setVisibility(View.INVISIBLE);
                }
                homeFragment.finalizar(nombreJugador,posicionJugador, fotoJugador);
                homeFragment.actualizarInfo(nombreJugador, posicionJugador);

            }
        });

        holder.imagenFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                relativeLayout = (RelativeLayout)v.getParent();
                jugadorElegido = relativeLayout.findViewById(R.id.textNombre);
                nombreJugador = jugadorElegido.getText().toString();
                posicionElegida = relativeLayout.findViewById(R.id.textPosicion);
                posicionJugador = posicionElegida.getText().toString();

                fotoJugador = listaJugadores.get(position).getFotoJugador();

                favNoOK = relativeLayout.findViewById(R.id.imageFav);
                favOK = relativeLayout.findViewById(R.id.imageFavOk);
                if(favNoOK.getVisibility()==View.VISIBLE){
                    favNoOK.setVisibility(View.INVISIBLE);
                    favOK.setVisibility(View.VISIBLE);
                }else{
                    favNoOK.setVisibility(View.VISIBLE);
                    favOK.setVisibility(View.INVISIBLE);
                }
                homeFragment.finalizar(nombreJugador,posicionJugador, fotoJugador); //Este metodo añadira el objeto en el que se pulse a convocados
                homeFragment.actualizarInfo(nombreJugador, posicionJugador); //Este metodo hara que el jugador seleccionado tenga "true" en su valor convocado

            }
        });

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
        ImageView imagenFav;
        ImageView imagenNoFav;
        View view;
        CardView cardView;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.relativePulsable);
            //PARA REFERENCIARLO USAMOS EL itemView:
            textoNombre = itemView.findViewById(R.id.textNombre);
            textoPosicion = itemView.findViewById(R.id.textPosicion);
            imagenJugador = itemView.findViewById(R.id.imageCard);
            imagenFav = itemView.findViewById(R.id.imageFav);
            imagenNoFav = itemView.findViewById(R.id.imageFavOk);
            cardView = itemView.findViewById(R.id.CardView);
        }

        public void asignarDatos(Jugador datosJugador) {

            // Obtén una referencia a la imagen que deseas descargar
            StorageReference imageRef = FirebaseStorage.getInstance().getReference().child(datosJugador.getFotoJugador());

            //UNA VEZ AQUI LE INDICAREMOS QUE ESTABLEZCA LOS VALORES CORRESPONDIENTES
            if(!datosJugador.isConvocado()){
                imagenFav.setVisibility(View.VISIBLE);
                imagenNoFav.setVisibility(View.INVISIBLE);
            }else{
                imagenNoFav.setVisibility(View.VISIBLE);
                imagenFav.setVisibility(View.INVISIBLE);
            }
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
