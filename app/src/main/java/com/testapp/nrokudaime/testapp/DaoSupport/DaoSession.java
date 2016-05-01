package com.testapp.nrokudaime.testapp.DaoSupport;

import android.database.sqlite.SQLiteDatabase;

import com.testapp.nrokudaime.testapp.DaoSupport.DaoEntities.DaoRecord;
import com.testapp.nrokudaime.testapp.Models.Record;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

public class DaoSession extends AbstractDaoSession{
    private final DaoConfig recodrDaoConfig;
    private final DaoRecord daoRecord;
    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);
        recodrDaoConfig = daoConfigMap.get(DaoRecord.class).clone();
        recodrDaoConfig.initIdentityScope(type);
        daoRecord = new DaoRecord(recodrDaoConfig, this);
        registerDao(Record.class, daoRecord);
    }

    public void clear() {
        recodrDaoConfig.getIdentityScope().clear();
    }

    public DaoRecord getDaoRecord() {
        return daoRecord;
    }
}
