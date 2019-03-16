package controller;

import java.io.File;

public class ThumbnailController {

    public void update(File res) {
        String path = res.getAbsolutePath();
        System.out.println(path);

    }
}
