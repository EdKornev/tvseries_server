package com.ek.serialsserver.picture.services;

import com.ek.serialsserver.index.services.FileService;
import com.ek.serialsserver.picture.models.PictureDoc;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Eduard on 19.05.2016.
 */
@Repository
public class PictureService {

    @Autowired private FileService fileService;
    @Autowired private MongoTemplate mongoTemplate;

    public GridFSDBFile getFileById(ObjectId id) throws NullPointerException {
        PictureDoc pictureDoc = mongoTemplate.findById(id, PictureDoc.class);
        return fileService.findById(pictureDoc.getOriginalFileId());
    }

    public ObjectId save(MultipartFile file) {
        try {
            GridFSFile gridFSFile = fileService.uploadFile(file);

            PictureDoc pictureDoc = new PictureDoc();
            pictureDoc.setFileName(gridFSFile.getFilename());
            pictureDoc.setContentType(gridFSFile.getContentType());
            pictureDoc.setOriginalFileId((ObjectId) gridFSFile.getId());

            mongoTemplate.save(pictureDoc);

            return pictureDoc.getId();
        } catch (Exception e) {
            return null;
        }
    }

    public void remove(ObjectId id) {
        PictureDoc pictureDoc = mongoTemplate.findById(id, PictureDoc.class);

        mongoTemplate.remove(pictureDoc);
        fileService.remove(pictureDoc.getOriginalFileId());
    }
}
