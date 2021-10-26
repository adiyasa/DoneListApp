package viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nauval.donelistapp.R;

public class DoneListVH extends RecyclerView.ViewHolder {
    public TextView tv_list ;
    public Button but_list;
    public DoneListVH(@NonNull View itemView) {
        super(itemView);
        tv_list = itemView.findViewById(R.id.tv_list);
        but_list = itemView.findViewById(R.id.but_option_list);
    }
}
