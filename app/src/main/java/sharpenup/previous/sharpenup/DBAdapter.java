// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package sharpenup.previous.sharpenup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;
    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    public static final String KEY_NAME = "name";
    public static final String KEY_handled = "handled";
    public static final String KEY_Parent = "Parent";
    public static final String KEY_Batch = "Batch";
    public static final String KEY_Centre = "Centre";
    public static final String KEY_MODE = "mode";
    public static final String KEY_NATURE = "nature";
    public static final String KEY_code_nature = "cd_nature";
    public static final String KEY_description_nature = "desc_nature";
    public static final String KEY_remarks_nature = "rem_nature";
    public static final String KEY_DETAIL = "comm_details";
    public static final String KEY_REMARKS = "remarks";
    public static final String KEY_ACTION_TAKEN_FLAG = "act_flag";
    public static final String KEY_ACTION_TAKEN = "act_to_be_taken";
    public static final String KEY_ACTION_TAKEN_CODE = "act_taken_cd";
    public static final String KEY_ACTION_TAKEN_DESCRIPTION = "act_taken_descript";
    public static final String KEY_ACTION_TAKEN_REMARKS = "act_taken_remarks";
    public static final String KEY_DATE = "date_pend";
    public static final String KEY_PERSON_RESPONSIBLE = "per_res";
    public static final String KEY_Time = "time";
    public static final String KEY_Date_LOG = "Date_log";
    public static final String KEY_Date_comp = "Comp_date";
    public static final String KEY_act_by = "act_by";
    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_NAME = 1;
    public static final int COL_handled = 2;
    public static final int COL_Parent = 3;
    public static final int COL_Batch = 4;
    public static final int COL_MODE = 5;
    public static final int COL_NATURE = 6;
    public static final int COL_code_nature = 7;
    public static final int COL_description_nature = 8;
    public static final int COL_remarks_nature = 9;
    public static final int COL_DETAIL = 10;
    public static final int COL_REMARKS = 11;
    public static final int COL_ACTION_TAKEN_FLAG = 12;
    public static final int COL_ACTION_TAKEN = 13;
    public static final int COL_ACTION_TAKEN_CODE = 14;
    public static final int COL_ACTION_TAKEN_DESCRIPTION = 15;
    public static final int COL_ACTION_TAKEN_REMARKS = 16;
    public static final int COL_DATE = 17;
    public static final int COL_PERSON_RESPONSIBLE = 18;
    public static final int COL_Time = 19;
    public static final int COL_Date_LOG = 20;
    public static final int COL_centre = 21;
    public static final int COL_completed_by = 22;
    public static final int COL_completed_date = 23;
    public static final String[] ALL_KEYS = new String[]{KEY_ROWID,
            KEY_NAME,
            KEY_handled,
            KEY_Parent,
            KEY_Batch,
            KEY_MODE,
            KEY_NATURE,
            KEY_code_nature,
            KEY_description_nature,
            KEY_remarks_nature,
            KEY_DETAIL,
            KEY_REMARKS,
            KEY_ACTION_TAKEN_FLAG,
            KEY_ACTION_TAKEN,
            KEY_ACTION_TAKEN_CODE,
            KEY_ACTION_TAKEN_DESCRIPTION,
            KEY_ACTION_TAKEN_REMARKS,
            KEY_DATE,
            KEY_PERSON_RESPONSIBLE,
            KEY_Time,
            KEY_Date_LOG,
            KEY_Centre, KEY_act_by, KEY_Date_comp};
    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb";
    public static final String DATABASE_TABLE = "mainTable";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 13;
    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " integer not null, "

			/*
			 * CHANGE 2:
			 */
                    // TODO: Place your fields here!
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_NAME + " text not null, "
                    + KEY_handled + " string not null, "
                    + KEY_Parent + " string not null,"
                    + KEY_Batch + " string not null,"
                    + KEY_MODE + " string not null,"
                    + KEY_NATURE + " string not null,"
                    + KEY_code_nature + " string not null,"
                    + KEY_description_nature + " string not null,"
                    + KEY_remarks_nature + " string not null,"
                    + KEY_DETAIL + " string not null,"
                    + KEY_REMARKS + " string not null,"
                    + KEY_ACTION_TAKEN_FLAG + " string not null,"
                    + KEY_ACTION_TAKEN + " string not null,"
                    + KEY_ACTION_TAKEN_CODE + " string not null,"
                    + KEY_ACTION_TAKEN_DESCRIPTION + " string not null,"
                    + KEY_ACTION_TAKEN_REMARKS + " string not null,"
                    + KEY_DATE + " integer not null,"
                    + KEY_PERSON_RESPONSIBLE + " string not null,"
                    + KEY_Time + " string not null,"
                    + KEY_Date_LOG + " integer not null,"
                    + KEY_Centre + " string not null,"
                    + KEY_act_by + " string not null,"
                    + KEY_Date_comp + " string not null"

                    // Rest  of creation:
                    + ");";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }


    // Add a new set of values to the database.
    public long insertRow(
            int serial,
            String name,
            String handled,
            String Parent,
            String Batch,
            String Centre,
            String MODE,
            String NATURE,
            String code_nature,
            String description_nature,
            String remarks_nature,
            String DETAIL,
            String REMARKS,
            String ACTION_TAKEN_FLAG,
            String ACTION_TAKEN,
            String ACTION_TAKEN_CODE,
            String ACTION_TAKEN_DESCRIPTION,
            String ACTION_TAKEN_REMARKS,
            int DATE,
            String PERSON_RESPONSIBLE,
            String Time,
            int Date_LOG,
            String act_by,
            String act_date
    ) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROWID, serial);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_handled, handled);
        initialValues.put(KEY_Parent, Parent);
        initialValues.put(KEY_Batch, Batch);
        initialValues.put(KEY_MODE, MODE);
        initialValues.put(KEY_NATURE, NATURE);
        initialValues.put(KEY_code_nature, code_nature);
        initialValues.put(KEY_description_nature, description_nature);
        initialValues.put(KEY_remarks_nature, remarks_nature);
        initialValues.put(KEY_DETAIL, DETAIL);
        initialValues.put(KEY_REMARKS, REMARKS);
        initialValues.put(KEY_ACTION_TAKEN_FLAG, ACTION_TAKEN_FLAG);
        initialValues.put(KEY_ACTION_TAKEN, ACTION_TAKEN);
        initialValues.put(KEY_ACTION_TAKEN_CODE, ACTION_TAKEN_CODE);
        initialValues.put(KEY_ACTION_TAKEN_DESCRIPTION, ACTION_TAKEN_DESCRIPTION);
        initialValues.put(KEY_ACTION_TAKEN_REMARKS, ACTION_TAKEN_REMARKS);
        initialValues.put(KEY_DATE, DATE);
        initialValues.put(KEY_PERSON_RESPONSIBLE, PERSON_RESPONSIBLE);
        initialValues.put(KEY_Time, Time);
        initialValues.put(KEY_Date_LOG, Date_LOG);
        initialValues.put(KEY_Centre, Centre);
        initialValues.put(KEY_act_by, act_by);
        initialValues.put(KEY_Date_comp, act_date);


        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        db.delete(DATABASE_TABLE, null, null);
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getSelectedRows(String name, String batch, String centre, String person_res, int date_plow, int date_phigh, int date_llow, int date_lhigh, int flag) {

        Cursor c;
        if (flag == 0) {
            c = db.query(true, DATABASE_TABLE, ALL_KEYS, "name=?", new String[]{name}
                    , null, null, null, null);
        } else if (flag == 2) {
            c = db.query(true, DATABASE_TABLE, ALL_KEYS, "Batch=?", new String[]{batch}
                    , null, null, null, null);
        } else if (flag == 3) {
            c = db.query(true, DATABASE_TABLE, ALL_KEYS, "Centre=?", new String[]{centre}
                    , null, null, null, null);
        } else if (flag == 1) {
            c = db.query(true, DATABASE_TABLE, ALL_KEYS, "per_res=?", new String[]{person_res}
                    , null, null, null, null);
        } else if (flag == 4) {
            c = db.rawQuery("SELECT * FROM mainTable WHERE Date_log BETWEEN " + date_llow + " AND " + date_lhigh, null);
        } else if (flag == 5) {
            c = db.rawQuery("SELECT * FROM mainTable WHERE date_pend BETWEEN " + date_plow + " AND " + date_phigh, null);
        } else c = null;


        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
/*	public boolean updateRow(long rowId,
			String name, 
			String handled,
			String Parent,
			String Batch,
			String MODE,
			String NATURE,
			String code_nature,
			String description_nature,
			String remarks_nature,
			String DETAIL,
			String REMARKS,
			String ACTION_TAKEN_FLAG,
			String ACTION_TAKEN,
			String ACTION_TAKEN_CODE,
			String ACTION_TAKEN_DESCRIPTION,
			String ACTION_TAKEN_REMARKS,
			String DATE,
			String PERSON_RESPONSIBLE,
			String Time,
			String Date_LOG
			
			
			
			
			
			) {
		String where = KEY_ROWID + "=" + rowId;

		
		 * CHANGE 4:
		 
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_NAME, name);
		newValues.put(KEY_handled, handled);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		newValues.put(KEY_Parent, Parent);
		
		
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	*/


    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:


            // Recreate new database:
            onCreate(_db);
        }
    }

}
