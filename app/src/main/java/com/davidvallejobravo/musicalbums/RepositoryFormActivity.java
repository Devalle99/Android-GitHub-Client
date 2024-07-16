package com.davidvallejobravo.musicalbums;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import models.Repository;
import models.RepositoryRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitclient.GithubApiClient;

public class RepositoryFormActivity extends AppCompatActivity {

    private EditText repoName, repoDescription;
    private Button btnSave, btnCancel;
    private GithubApiClient githubApiClient = GithubApiClient.getGithubApiClient();
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_repository_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repoName = findViewById(R.id.repoName);
        repoDescription = findViewById(R.id.repoDescription);
        btnSave = findViewById(R.id.saveButton);
        btnCancel = findViewById(R.id.cancelButton);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRepo(view);
            }
        });

        repository = (Repository) getIntent().getSerializableExtra("repository");
        if (repository != null) {
            repoName.setText(repository.getRepositoryName());
            repoDescription.setText(repository.getRepositoryLanguage()); // Ajusta según lo que desees mostrar
        }
    }

    public void saveRepo(View view) {
        String repoNameTxt = repoName.getText().toString();
        String repoDescriptionTxt = repoDescription.getText().toString();

        if (!repoNameTxt.isEmpty()) {
            String contentType = GithubApiClient.getContentType();
            String authorization = GithubApiClient.getToken();
            String apiVersion = GithubApiClient.getApiVersion();

            RepositoryRequest request = new RepositoryRequest(repoNameTxt, repoDescriptionTxt);

            Call<Repository> call = githubApiClient.createRepo(
                    contentType,
                    authorization,
                    apiVersion,
                    request
            );

            call.enqueue(new Callback<Repository>() {
                @Override
                public void onResponse(Call<Repository> call, Response<Repository> response) {
                    if (response.isSuccessful()) {
                        // Mostrar mensaje de exito
                        showToast("El repositorio se ha creado exitosamente");
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Repository> call, Throwable t) {
                    showToast("Hubo un error en el servidor");
                }
            });
        } else {
            // mostrar un toast
            showToast("Debe ingresar el nombre del repositorio para poder crearlo");
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}