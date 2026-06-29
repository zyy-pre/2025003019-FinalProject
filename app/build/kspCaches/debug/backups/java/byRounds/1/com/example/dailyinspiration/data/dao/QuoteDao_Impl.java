package com.example.dailyinspiration.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.dailyinspiration.data.entity.QuoteEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class QuoteDao_Impl implements QuoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QuoteEntity> __insertionAdapterOfQuoteEntity;

  private final SharedSQLiteStatement __preparedStmtOfCollectQuote;

  private final SharedSQLiteStatement __preparedStmtOfDeleteQuote;

  private final SharedSQLiteStatement __preparedStmtOfUncollectQuote;

  public QuoteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuoteEntity = new EntityInsertionAdapter<QuoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `quote` (`id`,`text`,`author`,`emotionTag`,`isCollected`,`collectedTime`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QuoteEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getText());
        statement.bindString(3, entity.getAuthor());
        statement.bindString(4, entity.getEmotionTag());
        final int _tmp = entity.isCollected() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getCollectedTime());
      }
    };
    this.__preparedStmtOfCollectQuote = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE quote SET isCollected = 1, emotionTag = ?, collectedTime = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteQuote = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM quote WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUncollectQuote = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE quote SET isCollected = 0 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertQuote(final QuoteEntity quote, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfQuoteEntity.insertAndReturnId(quote);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object collectQuote(final long id, final String emotionTag, final long collectedTime,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfCollectQuote.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, emotionTag);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, collectedTime);
        _argIndex = 3;
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
          __preparedStmtOfCollectQuote.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteQuote(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteQuote.acquire();
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
          __preparedStmtOfDeleteQuote.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object uncollectQuote(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUncollectQuote.acquire();
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
          __preparedStmtOfUncollectQuote.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<QuoteEntity>> getCollectedQuotes() {
    final String _sql = "SELECT * FROM quote WHERE isCollected = 1 ORDER BY collectedTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quote"}, new Callable<List<QuoteEntity>>() {
      @Override
      @NonNull
      public List<QuoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfEmotionTag = CursorUtil.getColumnIndexOrThrow(_cursor, "emotionTag");
          final int _cursorIndexOfIsCollected = CursorUtil.getColumnIndexOrThrow(_cursor, "isCollected");
          final int _cursorIndexOfCollectedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "collectedTime");
          final List<QuoteEntity> _result = new ArrayList<QuoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuoteEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpEmotionTag;
            _tmpEmotionTag = _cursor.getString(_cursorIndexOfEmotionTag);
            final boolean _tmpIsCollected;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCollected);
            _tmpIsCollected = _tmp != 0;
            final long _tmpCollectedTime;
            _tmpCollectedTime = _cursor.getLong(_cursorIndexOfCollectedTime);
            _item = new QuoteEntity(_tmpId,_tmpText,_tmpAuthor,_tmpEmotionTag,_tmpIsCollected,_tmpCollectedTime);
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
  public Flow<List<QuoteEntity>> getCollectedQuotesByTag(final String tag) {
    final String _sql = "SELECT * FROM quote WHERE isCollected = 1 AND emotionTag = ? ORDER BY collectedTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, tag);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quote"}, new Callable<List<QuoteEntity>>() {
      @Override
      @NonNull
      public List<QuoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfEmotionTag = CursorUtil.getColumnIndexOrThrow(_cursor, "emotionTag");
          final int _cursorIndexOfIsCollected = CursorUtil.getColumnIndexOrThrow(_cursor, "isCollected");
          final int _cursorIndexOfCollectedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "collectedTime");
          final List<QuoteEntity> _result = new ArrayList<QuoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuoteEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpEmotionTag;
            _tmpEmotionTag = _cursor.getString(_cursorIndexOfEmotionTag);
            final boolean _tmpIsCollected;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCollected);
            _tmpIsCollected = _tmp != 0;
            final long _tmpCollectedTime;
            _tmpCollectedTime = _cursor.getLong(_cursorIndexOfCollectedTime);
            _item = new QuoteEntity(_tmpId,_tmpText,_tmpAuthor,_tmpEmotionTag,_tmpIsCollected,_tmpCollectedTime);
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
  public Object getQuoteById(final long id, final Continuation<? super QuoteEntity> $completion) {
    final String _sql = "SELECT * FROM quote WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<QuoteEntity>() {
      @Override
      @Nullable
      public QuoteEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfEmotionTag = CursorUtil.getColumnIndexOrThrow(_cursor, "emotionTag");
          final int _cursorIndexOfIsCollected = CursorUtil.getColumnIndexOrThrow(_cursor, "isCollected");
          final int _cursorIndexOfCollectedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "collectedTime");
          final QuoteEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpEmotionTag;
            _tmpEmotionTag = _cursor.getString(_cursorIndexOfEmotionTag);
            final boolean _tmpIsCollected;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCollected);
            _tmpIsCollected = _tmp != 0;
            final long _tmpCollectedTime;
            _tmpCollectedTime = _cursor.getLong(_cursorIndexOfCollectedTime);
            _result = new QuoteEntity(_tmpId,_tmpText,_tmpAuthor,_tmpEmotionTag,_tmpIsCollected,_tmpCollectedTime);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getRandomCollectedQuote(final Continuation<? super QuoteEntity> $completion) {
    final String _sql = "SELECT * FROM quote WHERE isCollected = 1 ORDER BY RANDOM() LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<QuoteEntity>() {
      @Override
      @Nullable
      public QuoteEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfEmotionTag = CursorUtil.getColumnIndexOrThrow(_cursor, "emotionTag");
          final int _cursorIndexOfIsCollected = CursorUtil.getColumnIndexOrThrow(_cursor, "isCollected");
          final int _cursorIndexOfCollectedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "collectedTime");
          final QuoteEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpEmotionTag;
            _tmpEmotionTag = _cursor.getString(_cursorIndexOfEmotionTag);
            final boolean _tmpIsCollected;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCollected);
            _tmpIsCollected = _tmp != 0;
            final long _tmpCollectedTime;
            _tmpCollectedTime = _cursor.getLong(_cursorIndexOfCollectedTime);
            _result = new QuoteEntity(_tmpId,_tmpText,_tmpAuthor,_tmpEmotionTag,_tmpIsCollected,_tmpCollectedTime);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Integer> getCollectedCount() {
    final String _sql = "SELECT COUNT(*) FROM quote WHERE isCollected = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quote"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Object findQuoteByContent(final String text, final String author,
      final Continuation<? super QuoteEntity> $completion) {
    final String _sql = "SELECT * FROM quote WHERE text = ? AND author = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, text);
    _argIndex = 2;
    _statement.bindString(_argIndex, author);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<QuoteEntity>() {
      @Override
      @Nullable
      public QuoteEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfEmotionTag = CursorUtil.getColumnIndexOrThrow(_cursor, "emotionTag");
          final int _cursorIndexOfIsCollected = CursorUtil.getColumnIndexOrThrow(_cursor, "isCollected");
          final int _cursorIndexOfCollectedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "collectedTime");
          final QuoteEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpEmotionTag;
            _tmpEmotionTag = _cursor.getString(_cursorIndexOfEmotionTag);
            final boolean _tmpIsCollected;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCollected);
            _tmpIsCollected = _tmp != 0;
            final long _tmpCollectedTime;
            _tmpCollectedTime = _cursor.getLong(_cursorIndexOfCollectedTime);
            _result = new QuoteEntity(_tmpId,_tmpText,_tmpAuthor,_tmpEmotionTag,_tmpIsCollected,_tmpCollectedTime);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getRandomCollectedQuoteExcluding(final long excludeId,
      final Continuation<? super QuoteEntity> $completion) {
    final String _sql = "SELECT * FROM quote WHERE isCollected = 1 AND id != ? ORDER BY RANDOM() LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, excludeId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<QuoteEntity>() {
      @Override
      @Nullable
      public QuoteEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfEmotionTag = CursorUtil.getColumnIndexOrThrow(_cursor, "emotionTag");
          final int _cursorIndexOfIsCollected = CursorUtil.getColumnIndexOrThrow(_cursor, "isCollected");
          final int _cursorIndexOfCollectedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "collectedTime");
          final QuoteEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpEmotionTag;
            _tmpEmotionTag = _cursor.getString(_cursorIndexOfEmotionTag);
            final boolean _tmpIsCollected;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCollected);
            _tmpIsCollected = _tmp != 0;
            final long _tmpCollectedTime;
            _tmpCollectedTime = _cursor.getLong(_cursorIndexOfCollectedTime);
            _result = new QuoteEntity(_tmpId,_tmpText,_tmpAuthor,_tmpEmotionTag,_tmpIsCollected,_tmpCollectedTime);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
