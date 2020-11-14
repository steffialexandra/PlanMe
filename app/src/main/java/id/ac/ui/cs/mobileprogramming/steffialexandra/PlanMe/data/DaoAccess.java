package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {

    @Query("SELECT * FROM taskModel")
    List<TaskModel> getAll();

    @Query("SELECT * FROM taskModel WHERE taskid IN (:taskIds)")
    List<TaskModel> loadAllByIds(int[] taskIds);

    @Query("SELECT * FROM taskModel WHERE tasktitle LIKE :keyword")
    TaskModel findByName(String keyword);

    @Insert
    long insertTask(TaskModel task);

    @Update
    int updateTask(TaskModel task);

    @Delete
    void deleteTask(TaskModel task);

   /* @Query("DELETE FROM Item WHERE id = :itemId")
    int deleteItem(long itemId);*/
}