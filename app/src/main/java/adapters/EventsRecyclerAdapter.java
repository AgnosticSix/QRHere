package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upc.agnosticsix.qrhere.CustomItemClickListener;
import com.upc.agnosticsix.qrhere.R;
import com.upc.agnosticsix.qrhere.StudentsListActivity;

import java.util.List;

import model.Events;


public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.EventsViewHolder>{

    private List<Events> eventsList;
    CustomItemClickListener listener;
    Context context;
    //private final OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Events events);
    }

    public EventsRecyclerAdapter(Context context, List<Events> eventsList, CustomItemClickListener listener) {
        this.listener = listener;
        this.eventsList = eventsList;
        this.context = context;
    }

    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_recycler, parent, false);
        final EventsViewHolder mViewHolder = new EventsViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getAdapterPosition());

            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        holder.textViewNombre.setText(eventsList.get(position).getEvento());
        holder.textViewDescripcion.setText(eventsList.get(position).getDescripcion());
        holder.textViewCupo.setText(eventsList.get(position).getCupo());
    }

    @Override
    public int getItemCount() {
        //Log.v(EventsRecyclerAdapter.class.getSimpleName(),""+eventsList.size());
        return eventsList.size();
    }

    /**
     * ViewHolder class
     */
    public class EventsViewHolder extends ViewHolder {

        public AppCompatTextView textViewNombre;
        public AppCompatTextView textViewDescripcion;
        public AppCompatTextView textViewCupo;

        public EventsViewHolder(View view) {
            super(view);
            textViewNombre = (AppCompatTextView) view.findViewById(R.id.textViewNombre);
            textViewDescripcion = (AppCompatTextView) view.findViewById(R.id.textViewDescripcion);
            textViewCupo = (AppCompatTextView) view.findViewById(R.id.textViewCupo);
        }


    }
}
