package org.tomdroid.sync.baidu;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaFile;
import com.baidu.frontia.api.FrontiaStorage;
import com.baidu.frontia.api.FrontiaStorageListener;
import org.tomdroid.ui.Tomdroid;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changhong on 14-1-13.
 */
public class FileUtil {
    public static FrontiaStorage storage = Frontia.getStorage();
    public static ArrayList<String> remotefile = new ArrayList<String>();
    public static ArrayList<String> localfile = new ArrayList<String>();

    public static FrontiaFile getfile(String name) {

        FrontiaFile res = new FrontiaFile();
        res.setIsDir(false);
        res.setRemotePath("path" + Frontia.getCurrentAccount().getId() + "/" + name);
        if (Tomdroid.NOTES_PATH.endsWith("/")) {
            res.setNativePath(Tomdroid.NOTES_PATH + name);
        } else {

            res.setNativePath(Tomdroid.NOTES_PATH + "/" + name);
        }
        return res;
    }

    public static void pushall() {
        File file = new File(Tomdroid.NOTES_PATH);
        if (file.isFile()) {
            return;
        }
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".node");
            }
        };

        String[] locals = file.list(filenameFilter);

        for (String a : locals) {
            push(a);

        }

    }

    public static void push(String filename) {
        FrontiaFile file = getfile(filename);
        delete(file);
        storage.uploadFile(file, new FrontiaStorageListener.FileProgressListener() {
                    @Override
                    public void onProgress(String s, long l, long l2) {

                    }
                }, new FrontiaStorageListener.FileTransferListener() {
                    @Override
                    public void onSuccess(String s, String s2) {

                    }

                    @Override
                    public void onFailure(String s, int i, String s2) {

                    }
                }
        );
    }

    public static void pushnodelete(String filename) {
        FrontiaFile file = getfile(filename);
        storage.uploadFile(file, new FrontiaStorageListener.FileProgressListener() {
                    @Override
                    public void onProgress(String s, long l, long l2) {

                    }
                }, new FrontiaStorageListener.FileTransferListener() {
                    @Override
                    public void onSuccess(String s, String s2) {

                    }

                    @Override
                    public void onFailure(String s, int i, String s2) {

                    }
                }
        );
    }

    public static void delete(FrontiaFile filename) {
        storage.deleteFile(filename, new FrontiaStorageListener.FileOperationListener() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFailure(String s, int i, String s2) {

            }
        });
    }

    public static void pull(String remofilename) {
        FrontiaFile file = getfile(remofilename);
        storage.downloadFile(file, new FrontiaStorageListener.FileProgressListener() {
                    @Override
                    public void onProgress(String s, long l, long l2) {

                    }
                }, new FrontiaStorageListener.FileTransferListener() {
                    @Override
                    public void onSuccess(String s, String s2) {

                    }

                    @Override
                    public void onFailure(String s, int i, String s2) {

                    }
                }
        );
    }

    public static void getremotelist() {
        remotefile.clear();
        storage.listFiles(new FrontiaStorageListener.FileListListener() {
            @Override
            public void onSuccess(List<FrontiaFile> frontiaFiles) {
                for (FrontiaFile a : frontiaFiles) {
                    remotefile.add(a.getRemotePath());
                }
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    public static void getlocallist() {
        localfile.clear();
        File file = new File(Tomdroid.NOTES_PATH);
        for (String a : file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".node");
            }
        })) {
            localfile.add(a);
        }

    }

    public static void sync() {
        getlocallist();
        remotefile.clear();
        storage.listFiles(new FrontiaStorageListener.FileListListener() {
            @Override
            public void onSuccess(List<FrontiaFile> frontiaFiles) {
                for (FrontiaFile a : frontiaFiles) {
                    remotefile.add(pathtoname(a.getRemotePath()));
                }
                dosync();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    private static void dosync() {
        String temfile;
        for (String a : localfile) {
            if (remotefile.contains(a)) {
                push(a);

            } else {
                pushnodelete(a);

            }
        }
        for (String a : remotefile) {
            if (!localfile.contains(a)) {
                pull(a);

            }
        }

    }

    private static String pathtoname(String path) {
        int in = path.lastIndexOf("\")");
        return path.substring(in, path.length() - 1);

    }

}
