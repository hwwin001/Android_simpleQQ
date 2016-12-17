package com.example.yangenneng0.myapplication.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.yangenneng0.myapplication.db.DbConnection;
import com.example.yangenneng0.myapplication.model.MoodEntity;

import java.util.ArrayList;

/**
 * User: yangenneng
 * DateTime: 2016/12/17 17:48
 * Description:QQ说说数据访问对象
 */
public class MoodDAO {
    private static ArrayList<MoodEntity> moodEntityArrayList=null;//保存说说数据
    //获取所有说说
    public static ArrayList<MoodEntity> getmoodEntityArrayList(){
        if(null==moodEntityArrayList){
            synchronized ( MoodDAO.class ){
                if(null==moodEntityArrayList){
                    moodEntityArrayList=new ArrayList<MoodEntity>();
                }
            }

            //把数据库中已有的数据拿出来
            DbConnection connection=new DbConnection();
            SQLiteDatabase db=connection.getConnection();
            Cursor cursor=db.query("tb_mood",null,null,null,null,null,null);
            while ( cursor.moveToNext() ){
                int contentnum=cursor.getColumnIndex("content");
                int timenum=cursor.getColumnIndex("time");
                int personnum=cursor.getColumnIndex("person");

                String content=cursor.getString(contentnum);
                String time=cursor.getString(timenum);
                String person=cursor.getString(personnum);

                MoodEntity moodEntity=new MoodEntity(content,time,person);
                moodEntityArrayList.add(moodEntity);
                cursor.moveToNext();
            }

        }

        return moodEntityArrayList;
    }


    /**
     * 插入说说
     * @param moodEntity
     * @return
     */
    public boolean insert(MoodEntity moodEntity){
        try {
            moodEntityArrayList.add(moodEntity);
            DbConnection conn=new DbConnection();
            SQLiteDatabase db=conn.getConnection();
            String sql="insert into tb_mood(content,time,person) values('"
                    +moodEntity.getContent()+"','"+moodEntity.getTime()+"','"
                    +moodEntity.getPerson()+"')";
            db.execSQL(sql);
            db.close();
            return true;
        }catch ( Exception e ){
            return false;
        }

    }


}