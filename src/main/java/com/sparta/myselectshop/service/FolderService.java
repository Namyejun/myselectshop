package com.sparta.myselectshop.service;

import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.FolderResponseDto;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public void addFolders(List<String> folderNames, User user) {
        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);

        List<Folder> folderList = new ArrayList<>();

        for (String folderName : folderNames) {
            if (!isExistFolderName(folderName, existFolderList)) {
                Folder folder = new Folder(folderName, user);
                folderList.add(folder);
            } else {
                throw new IllegalArgumentException("duplicate folder name: " + folderName);
            }
        }

        folderRepository.saveAll(folderList);
    }

    public boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
        for (Folder folder : existFolderList) {
            if (folderName.equals(folder.getName())) {
                return true;
            }
        }
        return false;
    }

    public List<FolderResponseDto> getFolders(User user) {
        return folderRepository.findAllByUser(user).stream().map(FolderResponseDto::new).collect(Collectors.toList());
    }
}
