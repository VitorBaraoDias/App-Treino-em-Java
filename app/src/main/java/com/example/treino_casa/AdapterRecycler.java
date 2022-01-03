package com.example.treino_casa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {
    private Context context;
    private List<DadosTreinador> listadadosTreinador;

    AdapterRecycler(List<DadosTreinador> dadosTreinador) {
        this.context= this.context;
        this.listadadosTreinador=dadosTreinador;

    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView txt_NomeTreinador;
        TextView txt_LocalidadeTreinador;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.carView_Treinador);
            txt_NomeTreinador= (TextView) itemView.findViewById(R.id.txt_Nome_Recycler);
            txt_LocalidadeTreinador=(TextView) itemView.findViewById(R.id.txt_localidade_Recycler);

        }
    }

    @NonNull
    @Override
    public AdapterRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estrutura_recycler, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycler.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final DadosTreinador dadosTreinador = listadadosTreinador.get(position);
        holder.txt_NomeTreinador.setText("Nome: " +dadosTreinador.getNome());
        holder.txt_LocalidadeTreinador.setText("Localidade: "+dadosTreinador.getLocalidade());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listadadosTreinador.size();
    }
}
