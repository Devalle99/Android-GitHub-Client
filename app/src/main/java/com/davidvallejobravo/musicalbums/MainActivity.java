package com.davidvallejobravo.musicalbums;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adapters.RepositoryAdapter;
import models.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitclient.GithubApiClient;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Repository> repositories;
    private RepositoryAdapter repositoryAdapter;
    private FloatingActionButton fabNewRepository;
    private GithubApiClient apiClient = GithubApiClient.getGithubApiClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabNewRepository = findViewById(R.id.fabNewRepository);
        recyclerView = findViewById(R.id.recyclerView);
        fabNewRepository.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RepositoryFormActivity.class);
                startActivity(intent);
            }
        });
        this.loadRepositories();
        this.setupItemTouchHelper();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.loadRepositories();
    }

    public void loadRepositories() {
        repositories = new ArrayList<>();
        String contentType = GithubApiClient.getContentType();
        String authorization = GithubApiClient.getToken();
        String apiVersion = GithubApiClient.getApiVersion();

        Call<List<Repository>> call = apiClient.getRepos(contentType, authorization, apiVersion);
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if (response.isSuccessful()) {
                    repositories = response.body();
                    adaptList();
                } else {
                    showToast("Error en la llamada a la API");
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                showToast("Error de conexión");
            }
        });
    }

    private void adaptList() {
        repositoryAdapter = new RepositoryAdapter(repositories, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(repositoryAdapter);
    }

    private void setupItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    showSwipeActions(position);
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void showSwipeActions(int position) {
        new AlertDialog.Builder(this)
            .setTitle("Seleccione una acción")
            .setMessage("¿Qué desea hacer con este repositorio?")
            .setPositiveButton("Actualizar", (dialog, which) -> {
                Repository repo = repositories.get(position);
                Intent intent = new Intent(MainActivity.this, RepositoryFormActivity.class);
                intent.putExtra("repository", repo);
                startActivity(intent);
                repositoryAdapter.notifyItemChanged(position);
            })
            .setNegativeButton("Eliminar", (dialog, which) -> {
                Repository repo = repositories.get(position);
                deleteRepository(repo, position);
            })
            .setNeutralButton("Cancelar", (dialog, which) -> repositoryAdapter.notifyItemChanged(position)) // Restaurar el ítem al estado original.
            .show();
    }

    private void deleteRepository(Repository repo, int position) {
        String contentType = GithubApiClient.getContentType();
        String authorization = GithubApiClient.getToken();
        String apiVersion = GithubApiClient.getApiVersion();

        Call<Void> call = apiClient.deleteRepo(contentType, authorization, apiVersion, repo.getRepositoryName());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    repositories.remove(position);
                    repositoryAdapter.notifyItemRemoved(position);
                    showToast("Repositorio eliminado correctamente");
                } else {
                    showToast("Error al eliminar el repositorio");
                    repositoryAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast("Error de conexión");
                repositoryAdapter.notifyItemChanged(position);
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}