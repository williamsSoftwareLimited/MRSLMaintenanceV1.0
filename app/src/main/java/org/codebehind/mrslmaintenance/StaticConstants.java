package org.codebehind.mrslmaintenance;

/**
 * Created by Gavin on 18/01/2015.
 */
// this is where all the static constants should be kept
// There are some statics in the classes but will try to put them all here
public class StaticConstants {

    public static final String IMAGE_TITLE = "No title";
    public static final int BAD_DB = -1;
    // set the filter to the relevant tag to get the debug messages
    public static debugModes Debug = debugModes.debug; // set to false to get rid of debugging messages
    public static boolean DebugLog = false; // set to true to write to file
    public static final String EXTRA_REPORT_ID="org.CodeBehind.REPORT_ID";
    public static final String EXTRA_EQUIPMENT_ID="org.CodeBehind.SITE_EQUIP_ID";
    public static final String EXTRA_EQUIPMENT_MODE="org.CodeBehind.EQUIPMENT_MODE";
    public enum fragmentModes{ none, newer, alter, view, delete, add }
    public enum debugModes{none, debug, warning, error, off}

}
