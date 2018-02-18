package com.geekapps.andrii.gallerypicker.util;


import com.geekapps.andrii.gallerypicker.model.AlbumModel;
import com.geekapps.andrii.gallerypicker.model.MediaModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupHelper {

    public static List<AlbumModel> getAlbums(List<MediaModel> photoModels) {
        List<AlbumModel> consolidatedList = new ArrayList<>();
        HashMap<String, List<MediaModel>> map = groupsHashMap(photoModels);
        for (String albumName : map.keySet()) {
            AlbumModel dateItem = new AlbumModel(albumName, map.get(albumName));
            consolidatedList.add(dateItem);
        }
        return consolidatedList;
    }

    private static HashMap<String, List<MediaModel>> groupsHashMap(List<MediaModel> photoModels) {
        HashMap<String, List<MediaModel>> groupedHashMap = new HashMap<>();
        for (MediaModel photoModel : photoModels) {
            String hashMapKey = photoModel.getMediaAlbumName();
            if (groupedHashMap.containsKey(hashMapKey)) {
                groupedHashMap.get(hashMapKey).add(photoModel);
            } else {
                List<MediaModel> list = new ArrayList<>();
                list.add(photoModel);
                groupedHashMap.put(hashMapKey, list);
            }
        }
        return groupedHashMap;
    }

}
