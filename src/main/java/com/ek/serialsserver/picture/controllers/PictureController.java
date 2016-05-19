package com.ek.serialsserver.picture.controllers;

import com.ek.serialsserver.picture.routes.PictureRoutes;
import com.ek.serialsserver.picture.services.PictureService;
import com.mongodb.gridfs.GridFSDBFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Eduard on 19.05.2016.
 */
@Controller
public class PictureController {

    @Autowired private PictureService pictureService;

    @RequestMapping(value = PictureRoutes.GET_PICTURE, method = RequestMethod.GET)
    public void getPicture(HttpServletResponse response,@PathVariable ObjectId id) {
        try {
            GridFSDBFile gridFSDBFile = pictureService.getFileById(id);

            // Cash
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(new Date());
            gc.add(GregorianCalendar.YEAR, 1);
            long ex = gc.getTime().getTime();
            response.setDateHeader("Expires", ex);

            response.setHeader("Content-Disposition", "filename=\"" + cyrillicToLatin(gridFSDBFile.getFilename()) + "\"");
            response.setContentType(gridFSDBFile.getContentType());

            InputStream is = gridFSDBFile.getInputStream();
            IOUtils.copy(is, response.getOutputStream());
            is.close();

            response.flushBuffer();
        } catch (IOException ex) {
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", "/404");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", "/404");
        }
    }

    private String cyrillicToLatin(String lat) {
        if(lat == null) return lat;
        lat = lat.replaceAll("ё", "yo");
        lat = lat.replaceAll("а", "a");
        lat = lat.replaceAll("б", "b");
        lat = lat.replaceAll("в", "v");
        lat = lat.replaceAll("г", "g");
        lat = lat.replaceAll("д", "d");
        lat = lat.replaceAll("е", "e");
        lat = lat.replaceAll("ж", "zh");
        lat = lat.replaceAll("з", "z");
        lat = lat.replaceAll("и", "i");
        lat = lat.replaceAll("й", "y");
        lat = lat.replaceAll("к", "k");
        lat = lat.replaceAll("л", "l");
        lat = lat.replaceAll("м", "m");
        lat = lat.replaceAll("н", "n");
        lat = lat.replaceAll("о", "o");
        lat = lat.replaceAll("п", "p");
        lat = lat.replaceAll("р", "r");
        lat = lat.replaceAll("с", "s");
        lat = lat.replaceAll("т", "t");
        lat = lat.replaceAll("у", "u");
        lat = lat.replaceAll("ф", "f");
        lat = lat.replaceAll("х", "h");
        lat = lat.replaceAll("ц", "c");
        lat = lat.replaceAll("ч", "ch");
        lat = lat.replaceAll("ш", "sh");
        lat = lat.replaceAll("х", "shch");
        lat = lat.replaceAll("ь", "");
        lat = lat.replaceAll("ы", "y");
        lat = lat.replaceAll("ъ", "");
        lat = lat.replaceAll("э", "e");
        lat = lat.replaceAll("ю", "yu");
        lat = lat.replaceAll("я", "ya");

        lat = lat.replaceAll("Ё", "yo");
        lat = lat.replaceAll("А", "a");
        lat = lat.replaceAll("Б", "b");
        lat = lat.replaceAll("В", "v");
        lat = lat.replaceAll("Г", "g");
        lat = lat.replaceAll("Д", "d");
        lat = lat.replaceAll("Е", "e");
        lat = lat.replaceAll("Ж", "zh");
        lat = lat.replaceAll("З", "z");
        lat = lat.replaceAll("И", "i");
        lat = lat.replaceAll("Й", "y");
        lat = lat.replaceAll("К", "k");
        lat = lat.replaceAll("Л", "l");
        lat = lat.replaceAll("М", "m");
        lat = lat.replaceAll("Н", "n");
        lat = lat.replaceAll("О", "o");
        lat = lat.replaceAll("П", "p");
        lat = lat.replaceAll("Р", "r");
        lat = lat.replaceAll("С", "s");
        lat = lat.replaceAll("Т", "t");
        lat = lat.replaceAll("У", "u");
        lat = lat.replaceAll("Ф", "f");
        lat = lat.replaceAll("Х", "h");
        lat = lat.replaceAll("Ц", "c");
        lat = lat.replaceAll("Ч", "ch");
        lat = lat.replaceAll("Ш", "sh");
        lat = lat.replaceAll("Х", "shch");
        lat = lat.replaceAll("Ь", "");
        lat = lat.replaceAll("Ы", "y");
        lat = lat.replaceAll("Ъ", "");
        lat = lat.replaceAll("Э", "e");
        lat = lat.replaceAll("Ю", "yu");
        lat = lat.replaceAll("Я", "ya");
        return lat;
    }
}
