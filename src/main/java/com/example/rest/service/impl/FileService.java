package com.example.rest.service.impl;

import com.example.rest.model.entity.File;
import com.example.rest.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService implements com.example.rest.service.FileService {
    @Autowired
    FileRepository fileRepository;

    @Override
    public List<File> findAll() {
        return fileRepository.findAllFiles();
    }
}
