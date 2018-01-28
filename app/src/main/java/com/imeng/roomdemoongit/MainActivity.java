package com.imeng.roomdemoongit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.imeng.roomdemoongit.db.AppDataBase;
import com.imeng.roomdemoongit.db.Score;
import com.imeng.roomdemoongit.db.ScoreDao;
import com.imeng.roomdemoongit.db.User;
import com.imeng.roomdemoongit.db.UserDao;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    Button createDbBt = null;
    Button insertDbBt = null;
    Button deleteBt = null;
    Button queryBt = null;
    Button rxInsertBt = null;
    Button rxQueryBt = null;

    private AppDataBase mDb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

        createDb();
    }

    private void initView() {
        createDbBt = findViewById(R.id.crateDatabaseBt);
        insertDbBt = findViewById(R.id.insertDatabaseBt);
        deleteBt = findViewById(R.id.deleteDatabseBt);
        queryBt = findViewById(R.id.qureyDatabaseBt);
        rxInsertBt = findViewById(R.id.rxInsertBt);
        rxQueryBt = findViewById(R.id.rxQueryBt);

        createDbBt.setOnClickListener(this);
        insertDbBt.setOnClickListener(this);
        deleteBt.setOnClickListener(this);
        queryBt.setOnClickListener(this);
        rxInsertBt.setOnClickListener(this);
        rxQueryBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int vid = view.getId();
        switch (vid) {
            case R.id.crateDatabaseBt:
                createDb();
                break;
            case R.id.insertDatabaseBt:
                insertDb();
                break;
            case R.id.deleteDatabseBt:
                deleteData();
                break;
            case R.id.qureyDatabaseBt:
                queryData();
                break;
            case R.id.rxInsertBt:
                rxInsertDb();
                break;
            case R.id.rxQueryBt:
                rxQueryDb();
                break;
            default:
                break;
        }

    }

    private void createDb() {
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDb = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "database-name").build();
            }
        }).start();
        */

        mDb = App.getInstance().getDataBaseInstance();
        //暂时允许在mainthread 处理
        //mDb = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "database-name").allowMainThreadQueries().build();

    }

    private void insertDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, " insertDb: " + "");
                User user = new User();
                user.setUid((int) (System.currentTimeMillis() % 10000));
                user.setFirstName("First_" + System.currentTimeMillis());
                user.setLastName("Last_" + System.currentTimeMillis());
                if (!mDb.isOpen()) {
                    //Toast.makeText(MainActivity.this, "db is close", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, " thread  : db not open" + "");
                    //return;
                }
                /**
                 * 这几行是关键
                 */
                mDb.beginTransaction();
                //mDb.userDao().insertAll(user);
                mDb.userDao().insertA(user);
                //事务必须用这行么？？？
                mDb.setTransactionSuccessful();
                mDb.endTransaction();
                Log.d(TAG, " Insert Db suc !!!!!!!!!!!!!!!!!!! : " + "");
            }
        }).start();

        //mDb.close();
    }

    private void queryData() {
        Log.d(TAG, " onQueryData : " + "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                User[] users = null;
                users = mDb.userDao().loadAllUsers();
                for (User user : users) {
                    Log.e(TAG, user.getFirstName());
                }
            }
        }).start();
    }

    long count = 0l;
    private void rxInsertDb() {

        final UserDao dao = mDb.userDao();
        Flowable.range(0, 20).map(res -> {

            User user = new User();
            user.setUid((int) (System.currentTimeMillis() % 10000));
            user.setFirstName("First_" + res + "_" + System.currentTimeMillis());
            user.setLastName("Last_" + res + "_" + System.currentTimeMillis());

            if (!mDb.isOpen()) {
                //Toast.makeText(MainActivity.this, "db is close", Toast.LENGTH_SHORT).show();
                Log.d(TAG, " thread  : db not open" + "");
                //return 1;
            }
            /**
             * 这几行是关键
             */
            mDb.beginTransaction();
            //mDb.userDao().insertAll(user);
            long size = dao.insertA(user);
            //事务必须用这行么？？？
            mDb.setTransactionSuccessful();
            mDb.endTransaction();

            Log.i(TAG, "size: " + size);
            return size;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(every -> {
                    count = count + every.longValue();
                    Log.i(TAG, "onCreate: " + count);
                });

        final ScoreDao scoreDao = mDb.scoreDao();

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                //这里是做一些事情的。
                Score score = new Score();
                score.setScore("100");
                mDb.beginTransaction();
                scoreDao.insertA(score);
                mDb.setTransactionSuccessful();
                mDb.endTransaction();

                e.onNext(100);
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "flowable create insert 100");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "flowable throwable");
            }
        });



    }

    private void rxQueryDb() {
        //方法 A
        mDb.userDao().rxLoadAllUsers().flatMap(list->Flowable.fromIterable(list))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    Log.e(TAG, "UserID = " + user.getUid());
                });

        mDb.scoreDao().rxLoadAllScores().flatMap(list->Flowable.fromIterable(list))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(score -> {
                    Log.e(TAG, "ScoreID = " + score.getUid());
                });

        mDb.scoreDao().rxLoadLastScores()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(score -> {
                    Log.e(TAG, "ScoreID = " + score.getUid() + " Score = "+score.getScore());
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDb.isOpen()) {
            mDb.close();
        }
    }

    private void deleteData() {

    }
}
