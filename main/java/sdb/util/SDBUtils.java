package sdb.util;

import com.sequoiadb.base.Sequoiadb;

import java.util.ArrayList;

public class SDBUtils {
    private static final Sequoiadb sdb;

    /**
     * the driver that connects to sequoia DB
     */
    static {
        sdb = new Sequoiadb("120.24.52.13:11810", "sdbadmin", "sdbadmin");
    }

    /**
     * get all collection spaces
     *
     * @return ArrayList<String> of collection spaces
     */
    public static ArrayList<String> getCSNames() {
        return sdb.getCollectionSpaceNames();
    }

    /**
     * create a collection space
     *
     * @param cs collection space name that will be created
     */
    public static void createCS(String cs) {
        sdb.createCollectionSpace(cs);
        System.out.println("Create collection space successfully");
    }

    public static void dropCS(String cs) {
        sdb.dropCollectionSpace(cs);
        System.out.println("Drop collection space successfully");
    }

    /**
     * get all collections
     *
     * @return ArrayList<String> of collections
     */
    public static ArrayList<String> getCLNames() {
        return sdb.getCollectionNames();
    }

    /**
     * create a collection of "a collection space"
     *
     * @param cs a existing collection space name
     * @param cl collection name that will be created
     */
    public static void createCL(String cs, String cl) {
        sdb.getCollectionSpace(cs).createCollection(cl);
        System.out.println("Create collection successfully");
    }

    /**
     * drop a collection of "a collection space"
     *
     * @param cs a existing collection space name
     * @param cl collection name that will be dropped
     */
    public static void dropCL(String cs, String cl) {
        sdb.getCollectionSpace(cs).dropCollection(cl);
        System.out.println("Drop collection successfully");
    }

    public static String engine(String cs, String cl) {
        return "USING com.sequoiadb.spark OPTIONS ( host 'iambo:11810', collectionspace '" + cs
                + "', collection '" + cl + "', username 'sdbadmin', password 'sdbadmin')";
    }
}
