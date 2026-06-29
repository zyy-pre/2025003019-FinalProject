package com.example.dailyinspiration.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.dailyinspiration.data.dao.JournalDao;
import com.example.dailyinspiration.data.dao.JournalDao_Impl;
import com.example.dailyinspiration.data.dao.QuoteDao;
import com.example.dailyinspiration.data.dao.QuoteDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile QuoteDao _quoteDao;

  private volatile JournalDao _journalDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `quote` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `text` TEXT NOT NULL, `author` TEXT NOT NULL, `emotionTag` TEXT NOT NULL, `isCollected` INTEGER NOT NULL, `collectedTime` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `journal` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `quoteId` INTEGER NOT NULL, `userThought` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, FOREIGN KEY(`quoteId`) REFERENCES `quote`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_journal_quoteId` ON `journal` (`quoteId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '8e597be05461bc2a8f9706fef1920e70')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `quote`");
        db.execSQL("DROP TABLE IF EXISTS `journal`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsQuote = new HashMap<String, TableInfo.Column>(6);
        _columnsQuote.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuote.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuote.put("author", new TableInfo.Column("author", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuote.put("emotionTag", new TableInfo.Column("emotionTag", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuote.put("isCollected", new TableInfo.Column("isCollected", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuote.put("collectedTime", new TableInfo.Column("collectedTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuote = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuote = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuote = new TableInfo("quote", _columnsQuote, _foreignKeysQuote, _indicesQuote);
        final TableInfo _existingQuote = TableInfo.read(db, "quote");
        if (!_infoQuote.equals(_existingQuote)) {
          return new RoomOpenHelper.ValidationResult(false, "quote(com.example.dailyinspiration.data.entity.QuoteEntity).\n"
                  + " Expected:\n" + _infoQuote + "\n"
                  + " Found:\n" + _existingQuote);
        }
        final HashMap<String, TableInfo.Column> _columnsJournal = new HashMap<String, TableInfo.Column>(4);
        _columnsJournal.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournal.put("quoteId", new TableInfo.Column("quoteId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournal.put("userThought", new TableInfo.Column("userThought", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournal.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysJournal = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysJournal.add(new TableInfo.ForeignKey("quote", "CASCADE", "NO ACTION", Arrays.asList("quoteId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesJournal = new HashSet<TableInfo.Index>(1);
        _indicesJournal.add(new TableInfo.Index("index_journal_quoteId", false, Arrays.asList("quoteId"), Arrays.asList("ASC")));
        final TableInfo _infoJournal = new TableInfo("journal", _columnsJournal, _foreignKeysJournal, _indicesJournal);
        final TableInfo _existingJournal = TableInfo.read(db, "journal");
        if (!_infoJournal.equals(_existingJournal)) {
          return new RoomOpenHelper.ValidationResult(false, "journal(com.example.dailyinspiration.data.entity.JournalEntity).\n"
                  + " Expected:\n" + _infoJournal + "\n"
                  + " Found:\n" + _existingJournal);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "8e597be05461bc2a8f9706fef1920e70", "e97c97427a3e25ce89ede70fe9a9a5ba");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "quote","journal");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `quote`");
      _db.execSQL("DELETE FROM `journal`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(QuoteDao.class, QuoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(JournalDao.class, JournalDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public QuoteDao quoteDao() {
    if (_quoteDao != null) {
      return _quoteDao;
    } else {
      synchronized(this) {
        if(_quoteDao == null) {
          _quoteDao = new QuoteDao_Impl(this);
        }
        return _quoteDao;
      }
    }
  }

  @Override
  public JournalDao journalDao() {
    if (_journalDao != null) {
      return _journalDao;
    } else {
      synchronized(this) {
        if(_journalDao == null) {
          _journalDao = new JournalDao_Impl(this);
        }
        return _journalDao;
      }
    }
  }
}
