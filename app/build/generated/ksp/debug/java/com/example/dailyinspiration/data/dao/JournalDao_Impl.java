package com.example.dailyinspiration.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.dailyinspiration.data.entity.JournalEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class JournalDao_Impl implements JournalDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<JournalEntity> __insertionAdapterOfJournalEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteJournal;

  public JournalDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfJournalEntity = new EntityInsertionAdapter<JournalEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `journal` (`id`,`quoteId`,`userThought`,`createdAt`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final JournalEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getQuoteId());
        statement.bindString(3, entity.getUserThought());
        statement.bindLong(4, entity.getCreatedAt());
      }
    };
    this.__preparedStmtOfDeleteJournal = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM journal WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertJournal(final JournalEntity journal,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfJournalEntity.insertAndReturnId(journal);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteJournal(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteJournal.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteJournal.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<JournalEntity>> getJournalsByQuoteId(final long quoteId) {
    final String _sql = "SELECT * FROM journal WHERE quoteId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, quoteId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"journal"}, new Callable<List<JournalEntity>>() {
      @Override
      @NonNull
      public List<JournalEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfQuoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "quoteId");
          final int _cursorIndexOfUserThought = CursorUtil.getColumnIndexOrThrow(_cursor, "userThought");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<JournalEntity> _result = new ArrayList<JournalEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JournalEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpQuoteId;
            _tmpQuoteId = _cursor.getLong(_cursorIndexOfQuoteId);
            final String _tmpUserThought;
            _tmpUserThought = _cursor.getString(_cursorIndexOfUserThought);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new JournalEntity(_tmpId,_tmpQuoteId,_tmpUserThought,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<JournalEntity>> getAllJournals() {
    final String _sql = "SELECT * FROM journal ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"journal"}, new Callable<List<JournalEntity>>() {
      @Override
      @NonNull
      public List<JournalEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfQuoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "quoteId");
          final int _cursorIndexOfUserThought = CursorUtil.getColumnIndexOrThrow(_cursor, "userThought");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<JournalEntity> _result = new ArrayList<JournalEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JournalEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpQuoteId;
            _tmpQuoteId = _cursor.getLong(_cursorIndexOfQuoteId);
            final String _tmpUserThought;
            _tmpUserThought = _cursor.getString(_cursorIndexOfUserThought);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new JournalEntity(_tmpId,_tmpQuoteId,_tmpUserThought,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
