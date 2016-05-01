package com.testapp.nrokudaime.testapp.DaoSupport.DaoEntities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.testapp.nrokudaime.testapp.Models.Record;

import java.util.Date;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class DaoRecord extends AbstractDao<Record, Long> {

    public static final String TABLENAME = "RECORD";

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", false, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Content = new Property(2, String.class, "content", false, "CONTENT");
        public final static Property Image_Url = new Property(3, String.class, "image_url", false, "IMAGE_URL");
        public final static Property START_Date = new Property(4, Date.class, "start_date_formated", false, "START_DATE_NOT_FORMATED");
        public final static Property END_Date = new Property(5, Date.class, "end_date_formated", false, "END_DATE_NOT_FORMATED");
        public final static Property Tag = new Property(6, String.class, "tag", false, "TAG");
        public final static Property Organizer = new Property(7, String.class, "organizer", false, "ORGANIZER");
        public final static Property Place = new Property(8, String.class, "place", false, "PLACE");
        public static final Property InnerId = new Property(9, Long.class, "innerId", true, "INNERID");
    }

    public DaoRecord(DaoConfig config) {
        super(config);
    }

    public DaoRecord(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RECORD\" (" + //
                "\"_id\" INTEGER ," + // 1: id
                "\"TITLE\" TEXT NOT NULL ," + // 2: text
                "\"CONTENT\" TEXT NOT NULL ," + // 3: text
                "\"IMAGE_URL\" TEXT ," + // 4: text
                "\"START_DATE_NOT_FORMATED\" INTEGER ," + // 5: text
                "\"END_DATE_NOT_FORMATED\" INTEGER ," + // 6: text
                "\"TAG\" TEXT ," + // 7: text
                "\"ORGANIZER\" TEXT ," + // 8: text
                "\"PLACE\" TEXT ," +
                "\"INNERID\" INTEGER PRIMARY KEY AUTOINCREMENT);"); // 9
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, Record entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getTitle());

        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }

        String image_url = entity.getImage_url();
        if (image_url != null) {
            stmt.bindString(4, image_url);
        }

        Date start_date = entity.getStart_date_formated();
        if (start_date != null) {
            stmt.bindLong(5, start_date.getTime());
        }

        Date end_date = entity.getEnd_date_formated();
        if (end_date != null) {
            stmt.bindLong(6, end_date.getTime());
        }

        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(7, tag);
        }

        String organizer = entity.getOrganizer();
        if (organizer != null) {
            stmt.bindString(8, organizer);
        }

        String place = entity.getPlace();
        if (place != null) {
            stmt.bindString(9, place);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset) ? null : cursor.getLong(offset);
    }

    @Override
    public Record readEntity(Cursor cursor, int offset) {
        return new Record(
                cursor.isNull(offset) ? null : cursor.getLong(offset),
                cursor.getString(offset + 1),
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2),
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3),
                cursor.isNull(offset + 4) ? null : new Date(cursor.getLong(offset + 4)),
                cursor.isNull(offset + 5) ? null : new Date(cursor.getLong(offset + 5)),
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6),
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7),
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
    }

    @Override
    public void readEntity(Cursor cursor, Record entity, int offset) {
        entity.setId(cursor.isNull(offset) ? null : cursor.getLong(offset));
        entity.setTitle(cursor.getString(offset + 1));
        entity.setContent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setImage_url(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStart_date_formated(cursor.isNull(offset + 4) ? null : new Date(cursor.getLong(offset + 4)));
        entity.setEnd_date_formated(cursor.isNull(offset + 5) ? null : new Date(cursor.getLong(offset + 5)));
        entity.setTag(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setOrganizer(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setPlace(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
    }

    @Override
    protected Long updateKeyAfterInsert(Record entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    public Long getKey(Record entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
}
