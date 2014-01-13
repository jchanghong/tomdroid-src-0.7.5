package org.tomdroid.sync.baidu;

import android.util.Log;
import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaFile;
import com.baidu.frontia.api.FrontiaStorage;
import com.baidu.frontia.api.FrontiaStorageListener;
import org.tomdroid.Pool;
import org.tomdroid.ui.Tomdroid;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changhong on 14-1-13.
 */
public class FileUtil {
    public static String tag = "AAAAAAAAAA";
    public static FrontiaFile mfile;

    static {
        mfile = new FrontiaFile();
    }


    public static void ll(String info) {
        Log.i(tag, info);
    }

    public static FrontiaStorage storage = Frontia.getStorage();
    public static ArrayList<String> remotefile = new ArrayList<String>();
    public static ArrayList<String> localfile = new ArrayList<String>();

    private static void setFile(String name) {
        ll("in setFile");
//        mfile.setIsDir(false);
        mfile.setRemotePath("/path" + Frontia.getCurrentAccount().getId() + "/" + name);
        if (Tomdroid.NOTES_PATH.endsWith("/")) {
            mfile.setNativePath(Tomdroid.NOTES_PATH + name);
        } else {

            mfile.setNativePath(Tomdroid.NOTES_PATH + "/" + name);
        }

    }

    private static void pushall() {
        ll("pushall");
        getlocallist();

        for (String a : localfile) {
            push(a);

        }

    }

    private static void push(final String filename) {

        setFile(filename);
        delete(mfile);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        storage.uploadFile(mfile, fileProgressListener, fileTransferListener
        );
    }


    private static void pushnodelete(String filename) {
        setFile(filename);
        storage.uploadFile(mfile, fileProgressListener, fileTransferListener
        );
    }

    private static void delete(FrontiaFile filename) {
        storage.deleteFile(filename, fileOperationListener);
    }

    private static void pull(String remofilename) {
        setFile(remofilename);
        storage.downloadFile(mfile, fileProgressListener, new FrontiaStorageListener.FileTransferListener() {
            @Override
            public void onSuccess(String s, String s2) {
                ll("download " + s + ":" + s2);
            }

            @Override
            public void onFailure(String s, int i, String s2) {
                ll(s2);
            }
        }
        );
    }


    private static void getlocallist() {
        if (Tomdroid.NOTES_PATH.endsWith("/")) {
            Tomdroid.NOTES_PATH = Tomdroid.NOTES_PATH.substring(0, Tomdroid.NOTES_PATH.length() - 1);
//            ll(Tomdroid.NOTES_PATH);

        }
        localfile.clear();


        File file = new File(Tomdroid.NOTES_PATH);

        for (String a : file.list(new NotesFilter())) {
            localfile.add(pathtoname(a));

        }

    }
   static private class NotesFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".note"));
        }
    }
    public static void sync() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ll("in sync");
                getlocallist();
                remotefile.clear();
                storage.listFiles(new FrontiaStorageListener.FileListListener() {
                    @Override
                    public void onSuccess(List<FrontiaFile> frontiaFiles) {
                        for (FrontiaFile a : frontiaFiles) {
                            remotefile.add(pathtoname(a.getRemotePath()));
                        }
                        ll("locallist :" + localfile.size());
                        ll("relist:" + remotefile.size());
                        dosync();
                        ll("end sysc ");

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        ll(s);
                    }
                });

            }
        };
        Pool.exe(runnable);

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
        int in = path.lastIndexOf("/");
        if (in == -1) {
            return path;
        }
        return path.substring(in + 1, path.length());

    }

    private static FrontiaStorageListener.FileOperationListener fileOperationListener = new FrontiaStorageListener.FileOperationListener() {
        @Override
        public void onSuccess(String s) {
            ll(s + "  succuss");
        }

        @Override
        public void onFailure(String s, int i, String s2) {
            ll(s2);
        }
    };
    static FrontiaStorageListener.FileProgressListener fileProgressListener = new FrontiaStorageListener.FileProgressListener() {
        @Override
        public void onProgress(String s, long l, long l2) {

        }
    };
    static FrontiaStorageListener.FileTransferListener fileTransferListener = new FrontiaStorageListener.FileTransferListener() {
        @Override
        public void onSuccess(String s, String s2) {
            ll("succuss s:" + s + "s2:" + s2);
        }

        @Override
        public void onFailure(String s, int i, String s2) {

        }
    };

}
