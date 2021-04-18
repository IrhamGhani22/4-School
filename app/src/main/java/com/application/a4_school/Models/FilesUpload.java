package com.application.a4_school.Models;

import android.net.Uri;

import java.io.File;

public class FilesUpload {
    private String typefile;
    private String namefile;
    private String path;
    private Uri uri;
    private String realMime;
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getRealMime() {
        return realMime;
    }

    public void setRealMime(String realMime) {
        this.realMime = realMime;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getTypefile() {
        return typefile;
    }

    public void setTypefile(String typefile) {
        this.typefile = typefile;
    }

    public String getNamefile() {
        return namefile;
    }

    public void setNamefile(String namefile) {
        this.namefile = namefile;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
