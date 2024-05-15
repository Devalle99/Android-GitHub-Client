package com.davidvallejobravo.musicalbums;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import models.Repository;

public class RepositoryItem extends Fragment {
    private ImageView image;
    private TextView repositoryName, repositoryLanguage, repositoryOwner;
    private Repository repository;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.repository_item, container, false);
        repositoryName = view.findViewById(R.id.repositoryName);
        repositoryLanguage = view.findViewById(R.id.repositoryLanguage);
        repositoryOwner = view.findViewById(R.id.repositoryOwner);

        Glide.with(this).load(repository.getRepositoryOwner().getUserImage()).into(image);

        repositoryName.setText(repository.getRepositoryName());
        repositoryLanguage.setText(repository.getRepositoryLanguage());
        repositoryOwner.setText(repository.getRepositoryOwner().getUsername());

        return view;
    }
}
