package adapters;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upc.agnosticsix.qrhere.R;
import com.upc.agnosticsix.qrhere.StudentsListActivity;

import java.util.List;

import model.Events;


public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.EventsViewHolder>{

    private List<Events> eventsList;
    //private final OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Events events);
    }

    public EventsRecyclerAdapter(List<Events> eventsList) {
        //this.listener = listener;
        this.eventsList = eventsList;
    }

    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_recycler, parent, false);

        return new EventsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        holder.textViewNombre.setText(eventsList.get(position).getEvento());
        holder.textViewDescripcion.setText(eventsList.get(position).getDescripcion());
        holder.textViewCupo.setText(eventsList.get(position).getCupo());
    }

    @Override
    public int getItemCount() {
        Log.v(EventsRecyclerAdapter.class.getSimpleName(),""+eventsList.size());
        return eventsList.size();
    }

    /**
     * ViewHolder class
     */
    public class EventsViewHolder extends RecyclerView.ViewHolder {

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
