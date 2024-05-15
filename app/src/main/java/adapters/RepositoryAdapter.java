package adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.davidvallejobravo.musicalbums.R;
import java.util.List;
import models.Repository;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {
    private List<Repository> repositories;
    private Context context;
    public RepositoryAdapter(List<Repository> repositories, Context context) {
        this.repositories = repositories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.repository_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Repository repository = repositories.get(position);
        holder.setRepositoryData(repository);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView repositoryName, repositoryLanguage, repositoryOwner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            repositoryName = itemView.findViewById(R.id.repositoryName);
            repositoryLanguage = itemView.findViewById(R.id.repositoryLanguage);
            repositoryOwner = itemView.findViewById(R.id.repositoryOwner);
        }

        public void setRepositoryData(Repository repository) {
            Glide.with(context).load(repository.getRepositoryOwner().getUserImage()).into(image);
            repositoryName.setText(repository.getRepositoryName());
            repositoryLanguage.setText(repository.getRepositoryLanguage());
            repositoryOwner.setText(repository.getRepositoryOwner().getUsername());
        }
    }
}
