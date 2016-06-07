package com.ek.serialsserver.index.services;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Created by Eduard on 18.05.2016.
 */
@Repository
public class FileService {

    @Autowired private GridFsOperations gridFsTemplate;

    public GridFSFile uploadFile(MultipartFile file) throws Exception {
        GridFSFile gridFSFile = gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType());

        return gridFSFile;
    }

    public GridFSFile uploadFile(InputStream inputStream) throws Exception {
        GridFSFile gridFSFile = gridFsTemplate.store(inputStream, "image", "image/jpeg");

        return gridFSFile;
    }

    public void remove(ObjectId id) {
        Criteria criteria = new Criteria("_id").is(id);
        Query query = new Query(criteria);

        gridFsTemplate.delete(query);
    }

    public GridFSDBFile findById(ObjectId id) {
        Criteria criteria = new Criteria("_id").is(id);
        Query query = new Query(criteria);

        return gridFsTemplate.findOne(query);
    }
}
