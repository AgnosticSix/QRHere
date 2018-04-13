package adapters;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upc.agnosticsix.qrhere.R;

import java.util.List;

import model.Alumno;
import model.AlumnoEvento;

public class StudentsRecyclerAdapter extends RecyclerView.Adapter<StudentsRecyclerAdapter.StudentsViewHolder>{
    private List<Alumno> studentsList;

    public StudentsRecyclerAdapter(List<Alumno> studentsList) {
        this.studentsList = studentsList;
    }

    public StudentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_recycler, parent, false);

        return new StudentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentsViewHolder holder, int position) {
        holder.textViewMatricula.setText(studentsList.get(position).getMatricula());
        holder.textViewNombreS.setText(studentsList.get(position).getNombre());
        holder.textViewCarrera.setText(studentsList.get(position).getCarrera());
    }

    @Override
    public int getItemCount() {
        //Log.v(StudentsRecyclerAdapter.class.getSimpleName(),""+studentsList.size());
        return studentsList.size();
    }

    /**
     * ViewHolder class
     */
    public class StudentsViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewNombreS;
        public AppCompatTextView textViewCarrera;
        public AppCompatTextView textViewMatricula;

        public StudentsViewHolder(View view) {
            super(view);
            textViewMatricula = (AppCompatTextView) view.findViewById(R.id.textViewMatricula);
            textViewNombreS = (AppCompatTextView) view.findViewById(R.id.textViewNombreS);
            textViewCarrera = (AppCompatTextView) view.findViewById(R.id.textViewCarrera);
        }
    }
}
