package br.com.softmotions.apptodolist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.softmotions.apptodolist.R;
import br.com.softmotions.apptodolist.model.TodoNode;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoNodeAdapter extends RecyclerView.Adapter<TodoNodeAdapter.ViewHolder> {
    private Context context;
    private List<TodoNode> todoNodeList;
    private OnItemClickListener onItemClicked;
    private OnItemLongClickListener onItemLongClicked;

    public TodoNodeAdapter(Context context, List<TodoNode> todoNodeList, OnItemClickListener onItemClicked, OnItemLongClickListener onItemLongClicked) {
        this.context = context;
        this.todoNodeList = todoNodeList;
        this.onItemClicked = onItemClicked;
        this.onItemLongClicked = onItemLongClicked;
    }

    @Override
    public int getItemCount() {
        return this.todoNodeList != null ? this.todoNodeList.size() : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_tarefa_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //Update a view
        TodoNode t = todoNodeList.get(position);

        holder.tvTodo.setText(t.getTodo());

        if (t.isActive()) {
            if (!t.getData().equals("Not defined") && !t.getHora().equals("Not defined")) {
                holder.tvDataHora.setText(t.getData() + " at " + t.getHora());
            } else {
                holder.tvDataHora.setText("");
            }
        } else {
            holder.tvDataHora.setText("Completed " + t.getDataConclusion() + " at " + t.getHoraConclusion());
        }

        //Click
        if (onItemClicked != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClicked.onItemClicked(holder.itemView, position);
                }
            });
        }

        //Long press
        if (onItemLongClicked != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemLongClicked.onItemLongClicked(position);
                    return true;
                }
            });
        }
    }

    public interface OnItemClickListener {
        public void onItemClicked(View view, int position);
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_tv_todo)
        TextView tvTodo;
        @BindView(R.id.card_tv_data_hora)
        TextView tvDataHora;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }
}